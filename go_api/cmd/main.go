package main

import (
	api "github.com/jjh930301/needsss_global/pkg/api"
	"github.com/jjh930301/needsss_global/pkg/docs"
	swaggerFiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
	// _ "github.com/swaggo/gin-swagger/example/basic/docs"
)

// @securityDefinitions.apikey BearerAuth
// @in header
// @name Authorization
func main() {
	r := api.Router()

	// 127.0.0.1:7070/docs/index.html
	r.GET("/docs/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))

	docs.SwaggerInfo.Title = "needss-global"
	docs.SwaggerInfo.Description = `
		Use Bearer Token
	`
	r.Run(":3030")

}
