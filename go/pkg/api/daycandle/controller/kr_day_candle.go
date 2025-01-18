package daycandle

import (
	"github.com/gin-gonic/gin"
	daycandleservice "github.com/jjh930301/needsss_global/pkg/api/daycandle/service"
	"github.com/jjh930301/needsss_global/pkg/models/res"
	"github.com/jjh930301/needsss_global/pkg/structs"
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
	result := daycandleservice.GetKrDayCandle()
	res.Ok(
		c,
		"getting kr day candles",
		result,
		200,
	)
}
