package ticker

import (
	"fmt"

	"github.com/gin-gonic/gin"
	tickerservice "github.com/jjh930301/needsss_global/pkg/api/ticker/service"
	"github.com/jjh930301/needsss_global/pkg/models/res"
	"github.com/jjh930301/needsss_global/pkg/structs"
)

// @Tags ticker
// @Summary	krTicker
// @Description insert kr tickers
// @Accept json
// @Produce	json
// @Router /ticker/kr [get]
// @Security BearerAuth
func GetKrTicker(c *gin.Context) {
	_, verifyErr := c.Keys["member"].(structs.AuthClaim)
	if !verifyErr {
		return
	}
	err := tickerservice.GetKrTickers()
	if err != nil {
		fmt.Println(err)
		res.BadRequest(c, "cannot get tickers", 400)
		return
	}
	res.Ok(
		c,
		"getting kr tickers",
		true,
		200,
	)
}

// @Tags ticker
// @Summary kr ticker trend
// @Description insert ticker trend
// @Accept json
// @Produce json
// @Router /ticker/kr/trend [get]
// @Security BearerAuth
func GetKrTickerTrends(c *gin.Context) {
	_, verifyErr := c.Keys["member"].(structs.AuthClaim)
	if !verifyErr {
		return
	}
	err := tickerservice.GetKrTickerTrends()

	if err != nil {
		fmt.Println(err)
		res.BadRequest(c, "cannot get trends", 400)
		return
	}
	res.Ok(
		c,
		"getting kr trends",
		true,
		200,
	)
}
