package utils

import (
	"net/http"
	"strings"

	"github.com/gin-gonic/gin"
	"github.com/jjh930301/stock_global/pkg/models/res"
	"github.com/jjh930301/stock_global/pkg/structs"
)

func extractToken(r *http.Request) string {
	bearToken := r.Header.Get("Authorization")
	//normally Authorization the_token_xxx
	strArr := strings.Split(bearToken, "Bearer ")
	if len(strArr) == 2 {
		return strArr[1]
	}
	return ""
}

func Authenticator(c *gin.Context) {
	claims := structs.AuthClaim{}
	tokenString := extractToken(c.Request)
	if tokenString == "" {
		res.Forbidden(c, "", 403)
		c.Done()
		return
	}

	result, _ := Verification(tokenString, &claims)
	if result == nil {
		res.Unauthorized(c)
		c.Done()
		return
	}
	c.Set("member", claims)
	c.Next()
}
