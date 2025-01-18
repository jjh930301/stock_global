package utils

import (
	"log"
	"net/http"
	"time"

	"github.com/jjh930301/needsss_global/pkg/constant"
	"golang.org/x/net/proxy"
)

func TorClient() *http.Client {
	const maxRetries = 10

	for retries := 0; retries < maxRetries; retries++ {
		dialer, err := proxy.SOCKS5("tcp", constant.TorProxy, nil, proxy.Direct)
		if err != nil {
			log.Printf("Tor 연결 실패: %v (재시도 %d/%d)", err, retries+1, maxRetries)
			time.Sleep(5 * time.Second)
			continue
		}

		return &http.Client{
			Transport: &http.Transport{
				Dial: dialer.Dial,
			},
		}
	}

	log.Fatal("Tor 연결에 실패했습니다.")
	return nil
}
