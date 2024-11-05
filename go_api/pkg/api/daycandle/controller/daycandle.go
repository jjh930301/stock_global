package daycandle

import (
	"github.com/gin-gonic/gin"
	res "github.com/jjh930301/needsss_common/res"
	daycandleservice "github.com/jjh930301/needsss_global/pkg/api/daycandle/service"
)

// @Summary	day candles
// @Description	insert daycandles
// @Accept json
// @Produce	json
// @Router /ticker/daycandle [get]
func GetDayCandle(c *gin.Context) {
	result := daycandleservice.GetDayCandle()
	res.Ok(
		c,
		"message",
		gin.H{"result": result},
		200,
	)
}
