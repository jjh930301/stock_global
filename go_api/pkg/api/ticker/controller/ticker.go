package ticker

import (
	"github.com/gin-gonic/gin"
	"github.com/jjh930301/needsss_common/res"
	tickerservice "github.com/jjh930301/needsss_global/pkg/api/ticker/service"
)

const firstPage = 1

// @Summary	ticker test
// @Description	test
// @Accept json
// @Produce	json
// @Router /ticker [get]
func GetTickers(c *gin.Context) {
	// symbols := []string{"AAPL"}

	total := tickerservice.GetTickerAndInsert(firstPage)
	// 두번째 페이지부터 갖오면 됨
	for page := firstPage + 1; page <= total+1; page++ {
		go tickerservice.GetTickerAndInsert(page)
	}

	res.Ok(
		c,
		"getting data",
		gin.H{"result": true},
		200,
	)
}
