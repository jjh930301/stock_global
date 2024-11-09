package api

import (
	"net/http"
	"os"

	"github.com/gin-gonic/gin"
	daycandleController "github.com/jjh930301/needsss_global/pkg/api/daycandle/controller"
	tickerController "github.com/jjh930301/needsss_global/pkg/api/ticker/controller"
	"github.com/jjh930301/needsss_global/pkg/utils"
)

func Router() *gin.Engine {
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
		ticker.GET(Default, utils.Authenticator, tickerController.GetTickers)
		daycandle := ticker.Group("/daycandle")
		{
			daycandle.GET(Default, utils.Authenticator, daycandleController.GetDayCandle)
		}
	}

	return r
}
