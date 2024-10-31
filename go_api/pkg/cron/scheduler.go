package cron

import (
	"time"

	"github.com/go-co-op/gocron"
	"github.com/jjh930301/needsss_global/pkg/cron/scheduler"
)

const dayCandle = "daycandle"

func GoCron() *gocron.Scheduler {
	/*
		개장 시간: 13:30 UTC
		마감 시간: 20:00 UTC
	*/
	s := gocron.NewScheduler(time.UTC)
	s.Day().At("00:00").Do(scheduler.TickerCron)
	s.Cron("0 13 * * 1-5").Do(func() {
		s.Every(5).Seconds().Tag(dayCandle).Do(scheduler.WeekDayCandle)
	})
	s.Cron("0 21 * * 1-5").Do(func() {
		s.RemoveByTag(dayCandle)
	})

	return s
}
