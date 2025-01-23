package daycandleservice

import (
	"encoding/json"
	"fmt"
	"os"
	"sync"
	"time"

	candleDto "github.com/jjh930301/needsss_global/pkg/api/daycandle/dto"
	"github.com/jjh930301/needsss_global/pkg/models"
	"github.com/jjh930301/needsss_global/pkg/repositories"
	"github.com/jjh930301/needsss_global/pkg/utils"
)

func GetDayCandle(before int16) bool {
	tickers := repositories.TickerRepository{}.FindAll()
	batchSize := 100

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
				GetTickerChartsAndInsert(t.Symbol, t.ReutersCode, before)
			}(ticker)
		}
		innerWg.Wait()
	}
	return true
}

func GetTickerChartsAndInsert(
	ticker string,
	reuterCode string,
	day int16,
) {
	// client := utils.TorClient()
	b := time.Now().AddDate(0, 0, int(day))
	before := b.Format("20060102")
	now := time.Now().Format("20060102")
	url := fmt.Sprintf(os.Getenv("CHART_URL"), reuterCode, before, now)
	client := utils.TorClient()
	resp, err := client.Get(url)
	if err != nil {
		fmt.Println("tor error", err)
		return
	}
	defer resp.Body.Close()
	var dayCandleRes []candleDto.CandleResponse
	if err := json.NewDecoder(resp.Body).Decode(&dayCandleRes); err != nil {
		// GetTickerChartsAndInsert(ticker, reuterCode, day)
		fmt.Println(err)
		return
	}

	repositories.DayCandleRepository{}.BulkDuplicateKeyInsert(ticker, dayCandleRes)
}
