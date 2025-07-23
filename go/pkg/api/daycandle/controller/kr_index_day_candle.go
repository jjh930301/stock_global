package daycandle

import (
	"time"

	"github.com/gin-gonic/gin"
	dayCandleService "github.com/jjh930301/stock_global/pkg/api/daycandle/service"
	"github.com/jjh930301/stock_global/pkg/models/res"
	"github.com/jjh930301/stock_global/pkg/structs"
	"github.com/jjh930301/stock_global/pkg/utils"
)

// @Tags dayCandle
// @Summary	index day candles
// @Description	insert index daycandles
// @Accept json
// @Produce	json
// @Router /ticker/daycandle/kr/index [get]
// @Security BearerAuth
func GetKrIndexDayCandle(c *gin.Context) {
	_, verifyErr := c.Keys["member"].(structs.AuthClaim)
	if !verifyErr {
		return
	}
	startTime := time.Now().AddDate(0, 0, -500).In(utils.KrTime()).Format("20060102")
	now := time.Now().In(utils.KrTime()).Format("20060102")
	arr := []string{"KOSPI", "KOSDAQ"}
	for _, market := range arr {
		go dayCandleService.FetchKrIndexDayCandle(market, startTime, now)
	}
	res.Ok(
		c,
		"getting kr index day candles",
		true,
		200,
	)
}
