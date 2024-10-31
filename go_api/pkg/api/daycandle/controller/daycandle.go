package daycandle

import (
	"github.com/gin-gonic/gin"
	"github.com/jjh930301/needsss_common/res"
	daycandleservice "github.com/jjh930301/needsss_global/pkg/api/daycandle/service"
)

// @Summary	ticker test
// @Description	test
// @Accept json
// @Produce	json
// @Success	200	{string} string "ok"
// @Router /ticker/chart [get]
func GetDayCandle(c *gin.Context) {
	// https://api.stock.naver.com/chart/foreign/item/NFLX.O/day?&stockExchangeType=NASDAQ&startDateTime=20240101&endDateTime=20240104

	result := daycandleservice.GetDayCandle()
	res.Ok(
		c,
		"message",
		gin.H{"result": result},
		200,
	)
}
