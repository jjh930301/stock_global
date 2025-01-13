package cron

import (
	"time"

	"github.com/go-co-op/gocron"
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
	s.Every(1).Day().At("1:10").Do(scheduler.WeekDayCandle)
	s.Days().Every(5).Seconds().Do(func() {
		now := time.Now()
		isWithinTime := now.Hour() >= 13 && now.Hour() < 21

		if isWithinTime {
			scheduler.WeekDayCandle()
		}
	})
	/*
		KR
		개장 시간: 00:00 UTC
		마감 시간: 06:30 UTC
	*/
	s.Every(1).Day().At("15:00").Do(scheduler.KrTickerCron)

	return s
}
