package structs

import "github.com/dgrijalva/jwt-go"

type AuthClaim struct {
	UserID    string `json:"id"`
	Type      int    `json:"type"`
	AccountId string `json:"accountId"`
	jwt.StandardClaims
}
