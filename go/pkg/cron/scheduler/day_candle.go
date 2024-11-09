package scheduler

import (
	"sync"

	daycandleservice "github.com/jjh930301/needsss_global/pkg/api/daycandle/service"
	"github.com/jjh930301/needsss_global/pkg/models"
	"github.com/jjh930301/needsss_global/pkg/repositories"
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
			go func(t models.TickerModel) {
				defer innerWg.Done()
				daycandleservice.GetTickerChartsAndInsert(t.Symbol, t.ReutersCode, -10)
			}(ticker)
		}
		innerWg.Wait()
	}
}
