package repositories

import (
	"fmt"

	"github.com/jjh930301/needsss_global/pkg/db"
	"github.com/jjh930301/needsss_global/pkg/models"
	"github.com/jjh930301/needsss_global/pkg/structs"
	"gorm.io/gorm/clause"
)

type DayCandleRepository struct{}

func (d DayCandleRepository) BulkDuplicateKeyInsert(symbol string, candles []structs.CandleResponse) {
	var dayCandles []models.DayCandleModel
	for _, candle := range candles {
		t, _ := candle.ToTime()
		dayCandle := models.DayCandleModel{
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
		fmt.Println(result.Error)
	}
	fmt.Println("affected rows  ", int(result.RowsAffected))
}
