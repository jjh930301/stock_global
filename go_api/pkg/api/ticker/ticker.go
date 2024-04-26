package ticker

import (
	"time"

	finance "github.com/clebi/yfinance"
	"github.com/gin-gonic/gin"
	"github.com/jjh930301/needsss_common/res"
)

// @Summary	ticker test
// @Description	test
// @Accept json
// @Produce	json
// @Success	200	{string} string "ok"
// @Router /ticker [get]
func GetTickers(c *gin.Context) {
	// symbols := []string{"AAPL"}
	start := "2023-01-01"
	end := "2024-12-31"
	startDate, _ := time.Parse("2006-01-02", start)
	endDate, _ := time.Parse("2006-01-02", end)
	history , _ := finance.NewHistory().GetHistory(
		"AAPL",
		startDate,
		endDate,
	)
	res.Ok(
		c , 
		"message" , 
		history , 
		200)
}
