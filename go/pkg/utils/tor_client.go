package utils

import (
	"log"
	"net/http"
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
		"tor6:9050",
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
	proxyAddress := getProxy()

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
