package utils

import (
	"errors"
	"os"

	jwt "github.com/dgrijalva/jwt-go"
	"github.com/jjh930301/needsss_global/pkg/structs"
)

func Verification(
	tokenString string,
	claims *structs.AuthClaim,
	t int,
) (*jwt.Token, error) {
	key := func(token *jwt.Token) (interface{}, error) {
		_, ok := token.Method.(*jwt.SigningMethodHMAC)
		if !ok {
			return nil, errors.New("unexpected signing method")
		}
		return []byte(os.Getenv("JWT_ACCESS_SECRET")), nil
	}

	return jwt.ParseWithClaims(tokenString, claims, key)
}
