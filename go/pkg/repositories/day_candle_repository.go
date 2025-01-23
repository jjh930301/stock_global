package repositories

import (
	"fmt"

	candleDto "github.com/jjh930301/needsss_global/pkg/api/daycandle/dto"
	"github.com/jjh930301/needsss_global/pkg/db"
	"github.com/jjh930301/needsss_global/pkg/models"
	"gorm.io/gorm/clause"
)

type DayCandleRepository struct{}

func (d DayCandleRepository) BulkDuplicateKeyInsert(symbol string, candles []candleDto.CandleResponse) {
	var dayCandles []models.DayCandle
	for _, candle := range candles {
		t, _ := candle.ToTime()
		dayCandle := models.DayCandle{
			Symbol: symbol,
			Date:   t,
			Open:   candle.OpenPrice,
			Close:  candle.ClosePrice,
			High:   candle.HighPrice,
			Low:    candle.LowPrice,
			Volume: candle.AccumulatedTradingVolume,
		}
		dayCandles = append(dayCandles, dayCandle)
	}

	result := db.Database.Clauses(
		clause.OnConflict{UpdateAll: true},
	).Create(&dayCandles)
	if result.Error != nil {
		fmt.Println(symbol, result.Error)
	}
}
