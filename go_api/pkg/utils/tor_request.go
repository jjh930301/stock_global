package utils

import (
	"log"
	"net/http"

	"github.com/jjh930301/needsss_global/pkg/constant"
	"golang.org/x/net/proxy"
)

func TorClient() *http.Client {
	dialer, err := proxy.SOCKS5("tcp", constant.TorProxy, nil, proxy.Direct)
	if err != nil {
		log.Fatalf("setting tor proxy is failure%v", err)
	}
	transport := &http.Transport{
		Dial: dialer.Dial,
	}

	client := &http.Client{
		Transport: transport,
	}
	return client
}
