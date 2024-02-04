package api

import (
	"fmt"
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
	fmt.Println("swagger test")
	ticker := r.Group("/ticker")
	{
		ticker.GET(Default, tickerRouter.GetTickers)
	}

	return r
}
