package dayCandleService

import (
	"encoding/json"
	"fmt"
	"os"
	"sync"
	"time"

	candleDto "github.com/jjh930301/stock_global/pkg/api/daycandle/dto"
	"github.com/jjh930301/stock_global/pkg/models"
	"github.com/jjh930301/stock_global/pkg/repositories"
	"github.com/jjh930301/stock_global/pkg/utils"
)

func GetDayCandle(before int) bool {
	tickers := repositories.TickerRepository{}.FindAll()
	batchSize := 100
	for i := 0; i < len(tickers); i += batchSize {
		end := i + batchSize
		if end > len(tickers) {
			end = len(tickers)
		}

		batch := tickers[i:end]

		var innerWg sync.WaitGroup
		for _, krTicker := range batch {
			innerWg.Add(1)
			go func(t models.Ticker) {
				defer innerWg.Done()
				FetchTickerChartsAndInsert(t.Symbol, t.ReutersCode, t.MarKetType, int16(before))
			}(krTicker)
		}
		innerWg.Wait()
	}

	return true
}

func FetchTickerChartsAndInsert(
	ticker string,
	market string,
	reuterCode string,
	day int16,
) {
	// client := utils.TorClient()
	b := time.Now().AddDate(0, 0, int(day))
	before := b.Format("20060102")
	now := time.Now().Format("20060102")
	url := fmt.Sprintf(os.Getenv("CHART_URL"), reuterCode, market, before, now)
	client := utils.TorClient()
	resp, err := client.Get(url)
	if err != nil {
		go FetchTickerChartsAndInsert(ticker, reuterCode, market, day)
		return
	}
	defer resp.Body.Close()
	var dayCandleRes []candleDto.CandleResponse
	if err := json.NewDecoder(resp.Body).Decode(&dayCandleRes); err != nil {
		go FetchTickerChartsAndInsert(ticker, reuterCode, market, day)
		return
	}

	var dayCandles []models.DayCandle
	for _, candle := range dayCandleRes {
		t, _ := candle.ToTime()
		dayCandle := models.DayCandle{
			Symbol: ticker,
			Date:   t,
			Open:   candle.OpenPrice,
			Close:  candle.ClosePrice,
			High:   candle.HighPrice,
			Low:    candle.LowPrice,
			Volume: candle.AccumulatedTradingVolume,
		}
		dayCandles = append(dayCandles, dayCandle)
	}

	repositories.DayCandleRepository{}.BulkDuplicateKeyInsert(ticker, dayCandles)
}
