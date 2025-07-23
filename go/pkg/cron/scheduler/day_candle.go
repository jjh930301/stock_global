package scheduler

import (
	"time"

	dayCandleService "github.com/jjh930301/stock_global/pkg/api/daycandle/service"
	"github.com/jjh930301/stock_global/pkg/repositories"
	"github.com/jjh930301/stock_global/pkg/utils"
)

func WeekDayCandle() {
	tickers := repositories.TickerRepository{}.FindAll()
	for _, ticker := range tickers {
		go dayCandleService.FetchTickerChartsAndInsert(ticker.Symbol, ticker.ReutersCode, ticker.MarKetType, -10)
	}
}

func KrWeekDayCandle() {
	krTickers := repositories.KrTickerRepository{}.FindAll()
	now := time.Now().In(utils.KrTime()).Format("20060102")
	for _, ticker := range krTickers {
		go dayCandleService.FetchKrCandle(ticker.Symbol, now, now)
	}
}
