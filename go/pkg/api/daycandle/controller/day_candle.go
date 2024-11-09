package daycandle

import (
	"github.com/gin-gonic/gin"
	daycandleservice "github.com/jjh930301/needsss_global/pkg/api/daycandle/service"
	"github.com/jjh930301/needsss_global/pkg/models/res"
	"github.com/jjh930301/needsss_global/pkg/structs"
)

// @Summary	day candles
// @Description	insert daycandles
// @Accept json
// @Produce	json
// @Router /ticker/daycandle [get]
// @Security BearerAuth
func GetDayCandle(c *gin.Context) {
	_, verifyErr := c.Keys["member"].(structs.AuthClaim)
	if !verifyErr {
		return
	}
	result := daycandleservice.GetDayCandle()
	res.Ok(
		c,
		"message",
		gin.H{"result": result},
		200,
	)
}
