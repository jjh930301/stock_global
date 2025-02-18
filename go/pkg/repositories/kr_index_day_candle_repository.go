package repositories

import (
	"github.com/jjh930301/needsss_global/pkg/db"
	"github.com/jjh930301/needsss_global/pkg/models"
	"gorm.io/gorm/clause"
)

type KrIndexDayCandleRepository struct{}

func (k KrIndexDayCandleRepository) BulkDuplicatKeyInsert(dayCandles []models.KrIndexDayCandle) error {
	result := db.Database.Model(&models.KrIndexDayCandle{}).Clauses(
		clause.OnConflict{UpdateAll: true},
	).Create((dayCandles))
	if result.Error != nil {
		return result.Error
	}
	return nil
}
