package dayCandleService

import (
	"encoding/json"
	"fmt"
	"io"
	"os"
	"strings"
	"sync"
	"time"

	"github.com/jjh930301/stock_global/pkg/models"
	r "github.com/jjh930301/stock_global/pkg/redis"
	"github.com/jjh930301/stock_global/pkg/repositories"
	"github.com/jjh930301/stock_global/pkg/utils"
	"github.com/redis/go-redis/v9"
	"github.com/shopspring/decimal"
)

const (
	DATE   = 0
	OPEN   = 1
	HIGH   = 2
	LOW    = 3
	CLOSE  = 4
	VOLUME = 5
)

func GetKrDayCandle(before int) bool {
	krTickers := repositories.KrTickerRepository{}.FindAll()
	startTime := time.Now().AddDate(0, 0, before).In(utils.KrTime()).Format("20060102")
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
			go func(t models.KrTicker) {
				defer innerWg.Done()
				FetchKrCandle(t.Symbol, startTime, now)
			}(krTicker)
		}
		innerWg.Wait()
	}

	return true
}

func FetchKrCandle(symbol, startTime, endTime string) {
	url := fmt.Sprintf(
		os.Getenv("KR_CHART_URL"),
		symbol,
		startTime,
		endTime,
		"day",
	)
	client := utils.TorClient()
	res, err := client.Get(url)

	if err != nil {
		go FetchKrCandle(symbol, startTime, endTime)
		return
	}

	defer res.Body.Close()

	body, err := io.ReadAll(res.Body)
	if err != nil {
		go FetchKrCandle(symbol, startTime, endTime)
		return
	}

	replaceTap := strings.ReplaceAll(string(body), "\t", "")
	replaceNewLine := strings.ReplaceAll(replaceTap, "\n", "")
	replaceSpace := strings.ReplaceAll(replaceNewLine, " ", "")
	stringify := strings.ReplaceAll(replaceSpace, "'", "\"")

	var arr [][]interface{}
	err = json.Unmarshal([]byte(stringify), &arr)
	if err != nil {
		go FetchKrCandle(symbol, startTime, endTime)
		return
	}
	var krDayCandles []models.KrDayCandle
	for i, candle := range arr {
		if i == 0 {
			continue
		}
		datetime, _ := time.Parse("20060102", candle[DATE].(string))
		var open decimal.Decimal
		var high decimal.Decimal
		var low decimal.Decimal
		close := decimal.NewFromFloat(candle[CLOSE].(float64))
		if int(candle[OPEN].(float64)) == 0 {
			open = decimal.NewFromFloat(candle[CLOSE].(float64))
		} else {
			open = decimal.NewFromFloat(candle[OPEN].(float64))
		}

		if int(candle[LOW].(float64)) == 0 {
			low = decimal.NewFromFloat(candle[CLOSE].(float64))
		} else {
			low = decimal.NewFromFloat(candle[LOW].(float64))
		}

		if int(candle[HIGH].(float64)) == 0 {
			high = decimal.NewFromFloat(candle[CLOSE].(float64))
		} else {
			high = decimal.NewFromFloat(candle[HIGH].(float64))
		}

		krDayCandle := models.KrDayCandle{
			Symbol: symbol,
			Date:   datetime,
			Open:   open,
			High:   high,
			Low:    low,
			Close:  close,
			Volume: int64(candle[VOLUME].(float64)),
		}
		krDayCandles = append(krDayCandles, krDayCandle)
	}
	_ = repositories.KrDayCandleRepository{}.BulkDuplicatKeyInsert(krDayCandles)
	// redis stream
	json, err := json.Marshal(krDayCandles)
	if err != nil {
		return
	}
	_, err = r.Client.XAdd(r.RedisCtx, &redis.XAddArgs{
		Stream: "krDayCandleStream",
		Values: map[string]interface{}{
			"data": string(json),
		},
	}).Result()

	if err != nil {
		fmt.Printf("Redis Stream Push Error: %v\n", err)
		return
	}
}
