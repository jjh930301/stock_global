package dayCandleService

import (
	"encoding/json"
	"fmt"
	"io"
	"os"
	"strings"
	"time"

	"github.com/jjh930301/stock_global/pkg/models"
	r "github.com/jjh930301/stock_global/pkg/redis"
	"github.com/jjh930301/stock_global/pkg/repositories"
	"github.com/jjh930301/stock_global/pkg/utils"
	"github.com/redis/go-redis/v9"
	"github.com/shopspring/decimal"
)

func FetchKrIndexDayCandle(market, startTime, endTime string) {
	url := fmt.Sprintf(
		os.Getenv("KR_INDEX_URL"),
		market,
		startTime,
		endTime,
	)
	client := utils.TorClient()
	res, err := client.Get(url)

	if err != nil {

		go FetchKrCandle(market, startTime, endTime)
		return
	}

	defer res.Body.Close()

	body, err := io.ReadAll(res.Body)
	if err != nil {
		go FetchKrCandle(market, startTime, endTime)
		return
	}

	replaceTap := strings.ReplaceAll(string(body), "\t", "")
	replaceNewLine := strings.ReplaceAll(replaceTap, "\n", "")
	replaceSpace := strings.ReplaceAll(replaceNewLine, " ", "")
	stringify := strings.ReplaceAll(replaceSpace, "'", "\"")
	var arr [][]interface{}
	err = json.Unmarshal([]byte(stringify), &arr)
	if err != nil {
		go FetchKrCandle(market, startTime, endTime)
		return
	}
	var krIndexDayCandles []models.KrIndexDayCandle
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
		var marketId int16
		if market == "KOSPI" {
			marketId = 1
		} else {
			marketId = 2
		}
		krDayCandle := models.KrIndexDayCandle{
			Market: marketId,
			Date:   datetime,
			Open:   open,
			High:   high,
			Low:    low,
			Close:  close,
			Volume: int64(candle[VOLUME].(float64)),
		}
		krIndexDayCandles = append(krIndexDayCandles, krDayCandle)
	}
	_ = repositories.KrIndexDayCandleRepository{}.BulkDuplicatKeyInsert(krIndexDayCandles)
	// redis stream
	json, err := json.Marshal(krIndexDayCandles)
	if err != nil {
		return
	}
	_, err = r.Client.XAdd(r.RedisCtx, &redis.XAddArgs{
		Stream: "krIndexDayCandleStream",
		Values: map[string]interface{}{
			"data": string(json),
		},
	}).Result()

	if err != nil {
		fmt.Printf("Redis Stream Push Error: %v\n", err)
		return
	}
}
