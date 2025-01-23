package scheduler

import (
	tickerservice "github.com/jjh930301/needsss_global/pkg/api/ticker/service"
)

func TickerCron() {
	var firstPage = 1
	total, err := tickerservice.GetTicker(firstPage)
	if err != nil {
		return
	}
	// 두번째 페이지부터 갖오면 됨
	for page := firstPage + 1; page <= total+1; page++ {
		go tickerservice.GetTicker(page)
	}
}
