package daycandleservice

import (
	"encoding/json"
	"fmt"
	"io"
	"os"
	"strings"
	"sync"
	"time"

	"github.com/jjh930301/needsss_global/pkg/models"
	"github.com/jjh930301/needsss_global/pkg/repositories"
	"github.com/jjh930301/needsss_global/pkg/utils"
	"github.com/shopspring/decimal"
)

const (
	DATE   = 0
	OPEN   = 1
	HIGH   = 2
	LOW    = 3
	CLOSE  = 4
	VOLUME = 5
	PER    = 6
)

func GetKrDayCandle() bool {
	krTickers := repositories.KrTickerRepository{}.FindAll()
	startTime := time.Now().AddDate(-1, 0, 0).In(utils.KrTime()).Format("20060102")
	now := time.Now().In(utils.KrTime()).Format("20060102")
	batchSize := 100
	for i := 0; i < len(krTickers); i += batchSize {
		end := i + batchSize
		if end > len(krTickers) {
			end = len(krTickers)
		}

		batch := krTickers[i:end]

		var innerWg sync.WaitGroup
		for _, krTicker := range batch {
			innerWg.Add(1)
			go func(t models.KrTickerModel) {
				defer innerWg.Done()
				fetchKrCandle(t.Symbol, startTime, now)
			}(krTicker)
		}
		innerWg.Wait()
	}

	return true
}

func fetchKrCandle(symbol, startTime, endTime string) {
	url := fmt.Sprintf(
		os.Getenv("KR_CHART_URL"),
		symbol,
		startTime,
		endTime,
		"day",
	)
	client := utils.TorClient()
	req, err := client.Get(url)

	if err != nil {
		fmt.Println(err)
		return
	}
	defer req.Body.Close()

	body, err := io.ReadAll(req.Body)
	if err != nil {
		fmt.Println(err)
		return
	}

	replaceTap := strings.ReplaceAll(string(body), "\t", "")
	replaceNewLine := strings.ReplaceAll(replaceTap, "\n", "")
	replaceSpace := strings.ReplaceAll(replaceNewLine, " ", "")
	stringify := strings.ReplaceAll(replaceSpace, "'", "\"")
	var arr [][]interface{}
	_ = json.Unmarshal([]byte(stringify), &arr)
	var krDayCandles []models.KrDayCandleModel
	for i, candle := range arr {
		if i == 0 {
			continue
		}
		datetime, _ := time.Parse("20060102", candle[DATE].(string))
		open := decimal.NewFromFloat(candle[OPEN].(float64))
		high := decimal.NewFromFloat(candle[HIGH].(float64))
		low := decimal.NewFromFloat(candle[LOW].(float64))
		close := decimal.NewFromFloat(candle[CLOSE].(float64))
		per, _ := candle[PER].(float32)
		krDayCandle := models.KrDayCandleModel{
			Symbol:  symbol,
			Date:    datetime,
			Open:    open,
			High:    high,
			Low:     low,
			Close:   close,
			Volume:  int64(candle[VOLUME].(float64)),
			Percent: per,
		}

		krDayCandles = append(krDayCandles, krDayCandle)
	}
	fmt.Println("insert ", symbol)
	_ = repositories.KrDayCandleRepository{}.BulkDuplicatKeyInsert(krDayCandles)
}
