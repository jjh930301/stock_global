package cron

import (
	"os"
	"time"

	"github.com/go-co-op/gocron"
	tickerservice "github.com/jjh930301/needsss_global/pkg/api/ticker/service"
	"github.com/jjh930301/needsss_global/pkg/cron/scheduler"
)

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
	krCandleSeconds := 10
	if os.Getenv("ENV") == "local" {
		krCandleSeconds = 60
	}
	/* 일봉 */
	s.Every(krCandleSeconds).Seconds().Do(func() {
		now := time.Now().UTC()
		isWithinTime := now.Hour() >= 0 && now.Hour() < 7

		if isWithinTime {
			scheduler.KrWeekDayCandle()
		}
	})

	return s
}
