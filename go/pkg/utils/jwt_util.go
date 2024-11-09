package utils

import (
	"errors"
	"os"

	jwt "github.com/dgrijalva/jwt-go"
	"github.com/jjh930301/needsss_global/pkg/structs"
)

func Verification(tokenString string, claims *structs.AuthClaim) (*jwt.Token, error) {
	key := func(token *jwt.Token) (interface{}, error) {
		// 토큰의 서명 알고리즘을 확인합니다.
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, errors.New("unexpected signing method")
		}
		secret := os.Getenv("JWT_SECRET")
		if secret == "" {
			return nil, errors.New("JWT_SECRET environment variable not set")
		}
		return []byte(secret), nil
	}

	return jwt.ParseWithClaims(tokenString, claims, key)
}
