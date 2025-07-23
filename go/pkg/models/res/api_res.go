package res

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

type ResponseModel struct {
	Messages []string    `json:"messages"`
	Status   int         `json:"status"`
	Payload  interface{} `json:"payload"`
}

func Ok(
	c *gin.Context,
	message string,
	payload interface{},
	status int,
) {
	var messages []string = []string{message}

	model := ResponseModel{
		Messages: messages,
		Payload:  &payload,
		Status:   status,
	}
	c.JSON(http.StatusOK, model)
}

func Created(
	c *gin.Context,
	message string,
	payload interface{},
	status int,
) {
	var messages []string = []string{message}

	model := ResponseModel{
		Messages: messages,
		Payload:  &payload,
		Status:   status,
	}
	c.JSON(http.StatusCreated, model)

}

func BadRequest(
	c *gin.Context,
	message string,
	status int,
) {
	var messages []string = []string{message}
	model := ResponseModel{
		Messages: messages,
		Payload: gin.H{
			"reuslt": nil,
		},
		Status: status,
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
		Status: 4101,
	}
	c.JSON(http.StatusUnauthorized, model)

}

func Forbidden(
	c *gin.Context,
	message string,
	status int,
) {
	var messages []string = []string{"Cannot Access " + message}
	model := ResponseModel{
		Messages: messages,
		Payload: gin.H{
			"reuslt": nil,
		},
		Status: status,
	}
	c.JSON(http.StatusForbidden, model)

}

func ServerError(c *gin.Context) {
	var messages []string = []string{"server error"}

	model := ResponseModel{
		Messages: messages,
		Status:   5000,
		Payload: gin.H{
			"reuslt": nil,
		},
	}
	c.JSON(http.StatusInternalServerError, model)

}
