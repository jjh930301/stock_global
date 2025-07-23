package daycandle

import (
	"github.com/gin-gonic/gin"
	dayCandleService "github.com/jjh930301/stock_global/pkg/api/daycandle/service"
	"github.com/jjh930301/stock_global/pkg/models/res"
	"github.com/jjh930301/stock_global/pkg/structs"
)

// @Tags dayCandle
// @Summary	day candles
// @Description	insert daycandles
// @Accept json
// @Produce	json
// @Router /ticker/daycandle/kr [get]
// @Security BearerAuth
func GetKrDayCandle(c *gin.Context) {
	_, verifyErr := c.Keys["member"].(structs.AuthClaim)
	if !verifyErr {
		return
	}
	result := dayCandleService.GetKrDayCandle(-500)
	res.Ok(
		c,
		"getting kr day candles",
		result,
		200,
	)
}
