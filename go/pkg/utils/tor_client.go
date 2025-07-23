package utils

import (
	"log"
	"net/http"
	"os"
	"sync"

	"golang.org/x/net/proxy"
)

var (
	torProxies = []string{
		"tor1:9050",
		"tor2:9050",
		"tor3:9050",
		"tor4:9050",
		"tor5:9050",
		// "tor6:9050",
	}
	proxyIndex int
	mu         sync.Mutex
)

func getProxy() string {
	mu.Lock()
	defer mu.Unlock()

	proxy := torProxies[proxyIndex]
	proxyIndex = (proxyIndex + 1) % len(torProxies) // 순환
	return proxy
}

func TorClient() *http.Client {
	var proxyAddress string
	if os.Getenv("ENV") != "local" {
		proxyAddress = "127.0.0.1:9050"
	} else {
		// proxyAddress = getProxy()
		proxyAddress = "tor:9050"
	}

	dialer, err := proxy.SOCKS5("tcp", proxyAddress, nil, proxy.Direct)
	if err != nil {
		log.Fatalf("SOCKS5 다이얼러 생성 실패 (프록시: %s): %v", proxyAddress, err)
	}

	return &http.Client{
		Transport: &http.Transport{
			Dial: dialer.Dial,
		},
	}
}
