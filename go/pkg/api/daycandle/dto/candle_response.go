package candleDto

import (
	"time"

	"github.com/shopspring/decimal"
)

type CandleResponse struct {
	LocalDate                string          `json:"localDate"`
	ClosePrice               decimal.Decimal `json:"closePrice"`
	OpenPrice                decimal.Decimal `json:"openPrice"`
	HighPrice                decimal.Decimal `json:"highPrice"`
	LowPrice                 decimal.Decimal `json:"lowPrice"`
	AccumulatedTradingVolume int64           `json:"accumulatedTradingVolume"`
}

func (s *CandleResponse) ToTime() (time.Time, error) {
	parsedDate, err := time.Parse("20060102", s.LocalDate)
	if err != nil {
		return time.Time{}, err
	}
	return parsedDate, nil
}
