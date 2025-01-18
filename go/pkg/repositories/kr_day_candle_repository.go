package repositories

import (
	"github.com/jjh930301/needsss_global/pkg/db"
	"github.com/jjh930301/needsss_global/pkg/models"
	"gorm.io/gorm/clause"
)

type KrDayCandleRepository struct{}

func (k KrDayCandleRepository) BulkDuplicatKeyInsert(dayCandles []models.KrDayCandleModel) error {
	result := db.Database.Model(&models.KrDayCandleModel{}).Clauses(
		clause.OnConflict{UpdateAll: true},
	).Create((dayCandles))
	if result.Error != nil {
		return result.Error
	}
	return nil
}
