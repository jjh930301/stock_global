package repositories

import (
	"github.com/jjh930301/needsss_global/pkg/db"
	"github.com/jjh930301/needsss_global/pkg/models"
	"gorm.io/gorm/clause"
)

type KrTrendRepository struct{}

func (k KrTrendRepository) BulkDuplicateKeyInsert(trends []models.KrTrend) error {
	result := db.Database.Model(&models.KrTrend{}).Clauses(
		clause.OnConflict{UpdateAll: true},
	).Create(&trends)

	if result.Error != nil {
		return result.Error
	}
	return nil
}
