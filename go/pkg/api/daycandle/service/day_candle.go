package daycandleservice

import (
	"encoding/json"
	"fmt"
	"os"
	"sync"
	"time"

	"github.com/jjh930301/needsss_global/pkg/models"
	"github.com/jjh930301/needsss_global/pkg/repositories"
	"github.com/jjh930301/needsss_global/pkg/structs"
	"github.com/jjh930301/needsss_global/pkg/utils"
)

func GetDayCandle() bool {
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
			go func(t models.TickerModel) {
				defer innerWg.Done()
				GetTickerChartsAndInsert(t.Symbol, t.ReutersCode, -300)
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
		fmt.Println("cannot get ticker list")
		return
	}
	defer resp.Body.Close()
	var dayCandleRes []structs.CandleResponse
	if err := json.NewDecoder(resp.Body).Decode(&dayCandleRes); err != nil {
		fmt.Println(err)
		return
	}

	repositories.DayCandleRepository{}.BulkDuplicateKeyInsert(ticker, dayCandleRes)
}
