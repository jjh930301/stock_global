package cron

import (
	"time"

	"github.com/go-co-op/gocron"
	"github.com/jjh930301/needsss_global/pkg/cron/scheduler"
)

func GoCron() *gocron.Scheduler {
	s := gocron.NewScheduler(time.UTC)
	s.Every(5).Second().Do(scheduler.TickerCron)
	return s
}
