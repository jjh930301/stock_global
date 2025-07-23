package repositories

import (
	"github.com/jjh930301/stock_global/pkg/db"
	"github.com/jjh930301/stock_global/pkg/models"
	"gorm.io/gorm/clause"
)

type KrDayCandleRepository struct{}

func (k KrDayCandleRepository) BulkDuplicatKeyInsert(dayCandles []models.KrDayCandle) error {
	result := db.Database.Model(&models.KrDayCandle{}).Clauses(
		clause.OnConflict{UpdateAll: true},
	).Create((dayCandles))
	if result.Error != nil {
		return result.Error
	}
	return nil
}
