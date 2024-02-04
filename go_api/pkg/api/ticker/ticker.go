package ticker

import (
	"github.com/gin-gonic/gin"
)

// @Summary	패키지
// @Description	test
// @Accept json
// @Produce	json
// @Success	200	{string} string "ok"
// @Router /ticker [get]
func GetTickers(c *gin.Context) {
	c.Status(200)
}
