package scheduler

import (
	tickerservice "github.com/jjh930301/stock_global/pkg/api/ticker/service"
)

func TickerCron() {
	var firstPage = 1
	markets := []string{"NYSE", "NASDAQ"}
	for _, market := range markets {
		total, err := tickerservice.GetTicker(market, firstPage)
		if err != nil {
			return
		}
		// 두번째 페이지부터 갖오면 됨
		for page := firstPage + 1; page <= total+1; page++ {
			go tickerservice.GetTicker(market, page)
		}
	}

}
