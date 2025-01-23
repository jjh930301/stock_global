package main

import (
	"os"

	api "github.com/jjh930301/needsss_global/pkg/api"
	"github.com/jjh930301/needsss_global/pkg/cron"
	"github.com/jjh930301/needsss_global/pkg/db"
	"github.com/jjh930301/needsss_global/pkg/docs"
	"github.com/jjh930301/needsss_global/pkg/models"
	swaggerFiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
	// _ "github.com/swaggo/gin-swagger/example/basic/docs"
)

// @securityDefinitions.apikey BearerAuth
// @in header
// @name Authorization
func main() {
	var user string
	if os.Getenv("MYSQL_USER") == "" {
		user = "root"
	} else {
		user = os.Getenv("MYSQL_USER")
	}

	db.Database = db.InitDb(
		db.Database,
		user,
		os.Getenv("MYSQL_ROOT_PASSWORD"),
		os.Getenv("MYSQL_HOST"),
		os.Getenv("MYSQL_DATABASE"),
	)
	conn, err := db.Database.DB()
	if err != nil {
		panic(err)
	}
	conn.SetMaxIdleConns(500)
	conn.SetMaxOpenConns(500)

	if os.Getenv("ENV") == "local" {
		db.Database.Debug().AutoMigrate(
			&models.Ticker{},
			&models.Member{},
			&models.DayCandle{},
			&models.MemberHistory{},
			&models.KrTicker{},
			&models.KrDayCandle{},
			&models.KrTrend{},
		)
	}
	s := cron.GoCron()
	s.StartAsync()
	r := api.Router()

	// 127.0.0.1:8000/docs/index.html
	r.GET("/docs/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))

	docs.SwaggerInfo.Title = "api"
	docs.SwaggerInfo.Description = `
		Use Bearer Token
	`
	r.Run(":8000")

}
