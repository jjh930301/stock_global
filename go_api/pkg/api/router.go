package api

import (
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
	ticker := r.Group("/ticker")
	{
		ticker.GET(Default, tickerRouter.GetTickers)
	}

	return r
}
