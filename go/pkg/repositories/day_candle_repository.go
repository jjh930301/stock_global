package repositories

import (
	"fmt"

	"github.com/jjh930301/needsss_global/pkg/db"
	"github.com/jjh930301/needsss_global/pkg/models"
	"gorm.io/gorm/clause"
)

type DayCandleRepository struct{}

func (d DayCandleRepository) BulkDuplicateKeyInsert(symbol string, dayCandles []models.DayCandle) {

	result := db.Database.Clauses(
		clause.OnConflict{UpdateAll: true},
	).Create(&dayCandles)
	if result.Error != nil {
		fmt.Println(symbol, result.Error)
	}
}
