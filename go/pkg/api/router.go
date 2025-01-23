package api

import (
	"fmt"
	"net/http"
	"os"

	"github.com/gin-gonic/gin"
	daycandleController "github.com/jjh930301/needsss_global/pkg/api/daycandle/controller"
	tickerController "github.com/jjh930301/needsss_global/pkg/api/ticker/controller"
	"github.com/jjh930301/needsss_global/pkg/utils"
)

func Router() *gin.Engine {
	gin.SetMode(gin.DebugMode)
	r := gin.New()
	if os.Getenv("ENV") == "dev" {
		r.Use(gin.LoggerWithConfig(gin.LoggerConfig{
			SkipPaths: []string{"/health/check"},
		}))
	}
	r.GET("/healthcheck", func(c *gin.Context) {
		clientIP := c.ClientIP()
		c.JSON(http.StatusOK, gin.H{"ip": clientIP})
	})
	ticker := r.Group("/ticker")
	{
		ticker.GET(Kr, utils.Authenticator, tickerController.GetKrTicker)
		ticker.GET(Default, utils.Authenticator, tickerController.GetTickers)
		ticker.GET(fmt.Sprintf("%s%s", Kr, Trend), utils.Authenticator, tickerController.GetKrTickerTrends)
		daycandle := ticker.Group("/daycandle")
		{
			daycandle.GET(Kr, utils.Authenticator, daycandleController.GetKrDayCandle)
			daycandle.GET(Default, utils.Authenticator, daycandleController.GetDayCandle)
		}
	}
	return r
}
