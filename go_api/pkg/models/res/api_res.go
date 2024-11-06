package res

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

type ResponseModel struct {
	Messages   []string    `json:"messages"`
	ResultCode int         `json:"result_code"`
	Payload    interface{} `json:"payload"`
}

func Ok(
	c *gin.Context,
	message string,
	payload interface{},
	resultCode int,
) {
	var messages []string = []string{message}

	model := ResponseModel{
		Messages:   messages,
		Payload:    &payload,
		ResultCode: resultCode,
	}
	c.JSON(http.StatusOK, model)
}

func Created(
	c *gin.Context,
	message string,
	payload interface{},
	resultCode int,
) {
	var messages []string = []string{message}

	model := ResponseModel{
		Messages:   messages,
		Payload:    &payload,
		ResultCode: resultCode,
	}
	c.JSON(http.StatusCreated, model)

}

func BadRequest(
	c *gin.Context,
	message string,
	resultCode int,
) {
	var messages []string = []string{message}
	model := ResponseModel{
		Messages: messages,
		Payload: gin.H{
			"reuslt": nil,
		},
		ResultCode: resultCode,
	}
	c.JSON(http.StatusBadRequest, model)

}

func Unauthorized(
	c *gin.Context,
) {
	var messages []string = []string{"Required AccessToken"}
	model := ResponseModel{
		Messages: messages,
		Payload: gin.H{
			"reuslt": nil,
		},
		ResultCode: 4101,
	}
	c.JSON(http.StatusUnauthorized, model)

}

func Forbidden(
	c *gin.Context,
	message string,
	resultCode int,
) {
	var messages []string = []string{"Cannot Access " + message}
	model := ResponseModel{
		Messages: messages,
		Payload: gin.H{
			"reuslt": nil,
		},
		ResultCode: resultCode,
	}
	c.JSON(http.StatusForbidden, model)

}

func ServerError(c *gin.Context) {
	var messages []string = []string{"server error"}

	model := ResponseModel{
		Messages:   messages,
		ResultCode: 5000,
		Payload: gin.H{
			"reuslt": nil,
		},
	}
	c.JSON(http.StatusInternalServerError, model)

}
