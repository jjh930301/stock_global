package cron

import (
	"fmt"
	"runtime"
	"time"

	"github.com/go-co-op/gocron"
	dayCandleService "github.com/jjh930301/needsss_global/pkg/api/daycandle/service"
	tickerservice "github.com/jjh930301/needsss_global/pkg/api/ticker/service"
	"github.com/jjh930301/needsss_global/pkg/cron/scheduler"
	"github.com/jjh930301/needsss_global/pkg/utils"
)

func bToMb(b uint64) uint64 {
	return b / 1024 / 1024
}

func GoCron() *gocron.Scheduler {

	s := gocron.NewScheduler(time.UTC)
	/*
		US
		개장 시간: 13:30 UTC
		마감 시간: 20:00 UTC
	*/
	// update tickers
	s.Every(1).Day().At("1:00").Do(scheduler.TickerCron)
	// update day_candles
	s.Days().Every(5).Seconds().Do(func() {
		now := time.Now()
		isWithinTime := now.Hour() >= 13 && now.Hour() < 21

		if isWithinTime {
			scheduler.WeekDayCandle()
		}
	})
	/* 종목 */
	s.Every(1).Day().At("15:00").Do(tickerservice.GetKrTickers)
	/* 매매동향 */
	s.Every(1).Day().At("16:00").Do(tickerservice.GetKrTickerTrends)
	/*
		KR
		개장 시간: 00:00 UTC
		마감 시간: 06:30 UTC
	*/
	/* 일봉 */
	s.Every(10).Seconds().Do(func() {
		// index day candle
		now := time.Now().UTC()
		today := time.Now().Weekday()
		if today == time.Sunday || today == time.Saturday {
			return
		}
		isWithinTime := now.Hour() >= 0 && now.Hour() < 7
		if !isWithinTime {
			return
		}
		var m runtime.MemStats
		runtime.ReadMemStats(&m)
		fmt.Println(isWithinTime, bToMb(m.Alloc))
		if bToMb(m.Alloc) > 150 {
			return
		}

		krTime := time.Now().In(utils.KrTime()).Format("20060102")
		arr := []string{"KOSPI", "KOSDAQ"}
		for _, market := range arr {
			go dayCandleService.FetchKrIndexDayCandle(market, krTime, krTime)
		}
		scheduler.KrWeekDayCandle()
	})

	return s
}
