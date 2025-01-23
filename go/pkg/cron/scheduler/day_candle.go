package scheduler

import (
	"sync"
	"time"

	daycandleservice "github.com/jjh930301/needsss_global/pkg/api/daycandle/service"
	"github.com/jjh930301/needsss_global/pkg/models"
	"github.com/jjh930301/needsss_global/pkg/repositories"
	"github.com/jjh930301/needsss_global/pkg/utils"
)

func WeekDayCandle() {
	tickers := repositories.TickerRepository{}.FindAll()
	batchSize := 500

	for i := 0; i < len(tickers); i += batchSize {
		end := i + batchSize
		if end > len(tickers) {
			end = len(tickers)
		}

		batch := tickers[i:end]

		var innerWg sync.WaitGroup
		for _, ticker := range batch {
			innerWg.Add(1)
			go func(t models.Ticker) {
				defer innerWg.Done()
				daycandleservice.GetTickerChartsAndInsert(t.Symbol, t.ReutersCode, -10)
			}(ticker)
		}
		innerWg.Wait()
	}
}

func KrWeekDayCandle() {
	krTickers := repositories.KrTickerRepository{}.FindAll()
	batchSize := 200
	now := time.Now().In(utils.KrTime()).Format("20060102")
	for i := 0; i < len(krTickers); i += batchSize {
		end := i + batchSize
		if end > len(krTickers) {
			end = len(krTickers)
		}

		batch := krTickers[i:end]

		var innerWg sync.WaitGroup
		for _, krTicker := range batch {
			innerWg.Add(1)
			go func(kt models.KrTicker) {
				defer innerWg.Done()
				daycandleservice.FetchKrCandle(kt.Symbol, now, now)
			}(krTicker)
		}
		innerWg.Wait()
	}
	daycandleservice.GetKrDayCandle(0)
}
