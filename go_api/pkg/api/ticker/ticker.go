package ticker

import (
	"encoding/json"
	"fmt"
	"sync"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/jjh930301/needsss_common/res"
	"github.com/jjh930301/needsss_global/pkg/constant"
	"github.com/jjh930301/needsss_global/pkg/models"
	"github.com/jjh930301/needsss_global/pkg/repositories"
	"github.com/jjh930301/needsss_global/pkg/utils"

	"github.com/jjh930301/needsss_global/pkg/structs"
)

const firstPage = 1
const size = 100

// @Summary	ticker test
// @Description	test
// @Accept json
// @Produce	json
// @Router /ticker [get]
func GetTickers(c *gin.Context) {
	// symbols := []string{"AAPL"}

	total := getTickerAndInsert(firstPage)
	// 두번째 페이지부터 갖오면 됨
	for page := firstPage + 1; page <= total+1; page++ {
		go getTickerAndInsert(page)
	}

	res.Ok(
		c,
		"getting data",
		gin.H{"result": true},
		200,
	)
}

func getTickerAndInsert(page int) int {
	url := fmt.Sprintf(constant.TickerUri, page, size)

	client := utils.TorClient()
	resp, err := client.Get(url)
	if err != nil {
		fmt.Println("cannot get ticker list")
	}
	defer resp.Body.Close()

	var stockRes structs.StockResponse
	// var stockRes json.RawMessage
	if err := json.NewDecoder(resp.Body).Decode(&stockRes); err != nil {
		fmt.Println(err)
	}
	err = repositories.TickerRepository{}.BulkDuplicateKeyInsert(stockRes.Stocks)
	if err != nil {
		fmt.Println(err)
	}
	return stockRes.TotalCount / size
}

// @Summary	ticker test
// @Description	test
// @Accept json
// @Produce	json
// @Success	200	{string} string "ok"
// @Router /ticker/chart [get]
func GetTickerChart(c *gin.Context) {
	// https://api.stock.naver.com/chart/foreign/item/NFLX.O/day?&stockExchangeType=NASDAQ&startDateTime=20240101&endDateTime=20240104
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
				getTickerChartsAndInsert(t.Symbol, t.ReutersCode)
			}(ticker)
		}
		innerWg.Wait()
	}

	res.Ok(
		c,
		"message",
		gin.H{"result": true},
		200,
	)
}

func getTickerChartsAndInsert(ticker string, reuterCode string) {
	// client := utils.TorClient()
	b := time.Now().AddDate(0, 0, -15)
	before := b.Format("20060102")
	now := time.Now().Format("20060102")
	url := fmt.Sprintf(constant.TickerChartUri, reuterCode, before, now)
	client := utils.TorClient()
	resp, err := client.Get(url)
	if err != nil {
		fmt.Println("cannot get ticker list")
	}
	defer resp.Body.Close()
	var dayCandleRes []structs.CandleResponse
	if err := json.NewDecoder(resp.Body).Decode(&dayCandleRes); err != nil {
		fmt.Println(err)
		return
	}

	repositories.DayCandleRepository{}.BulkDuplicateKeyInsert(ticker, dayCandleRes)
}
