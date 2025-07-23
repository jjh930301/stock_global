package ticker

import (
	"github.com/gin-gonic/gin"
	tickerservice "github.com/jjh930301/stock_global/pkg/api/ticker/service"
	"github.com/jjh930301/stock_global/pkg/models/res"
	"github.com/jjh930301/stock_global/pkg/structs"
)

const firstPage = 1

// @Tags ticker
// @Summary	ticker
// @Description insert tickers
// @Accept json
// @Produce	json
// @Router /ticker [get]
// @Security BearerAuth
func GetTickers(c *gin.Context) {
	_, verifyErr := c.Keys["member"].(structs.AuthClaim)
	if !verifyErr {
		return
	}
	markets := []string{"NYSE", "NASDAQ"}
	for _, market := range markets {
		total, err := tickerservice.GetTicker(market, firstPage)
		if err != nil {
			res.ServerError(c)
			return
		}
		// 두번째 페이지부터 갖오면 됨
		for page := firstPage + 1; page <= total+1; page++ {
			go tickerservice.GetTicker(market, page)
		}
	}

	res.Ok(
		c,
		"getting data",
		true,
		200,
	)
}
