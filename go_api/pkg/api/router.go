package api

import (
	"fmt"
	"net/http"
	"os"

	"github.com/gin-gonic/gin"
	tickerRouter "github.com/jjh930301/needsss_global/pkg/api/ticker"
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
		fmt.Println(clientIP)
		c.JSON(http.StatusOK, gin.H{"ip": clientIP})
	})
	ticker := r.Group("/ticker")
	{
		ticker.GET(Default, tickerRouter.GetTickers)
		ticker.GET(TickerChart, tickerRouter.GetTickerChart)
	}

	return r
}
