package repositories

import (
	"github.com/jjh930301/needsss_global/pkg/db"
	"github.com/jjh930301/needsss_global/pkg/models"
	"gorm.io/gorm/clause"
)

type KrTickerRepository struct{}

func (k KrTickerRepository) FindAll() []models.KrTicker {
	var krTickers []models.KrTicker
	db.Database.Model(
		&models.KrTicker{},
	).Select("symbol").Find(&krTickers)
	return krTickers
}

func (k KrTickerRepository) BulkDuplicateKeyInsert(tickers []models.KrTicker) error {
	result := db.Database.Model(
		&models.KrTicker{},
	).Clauses(
		clause.OnConflict{UpdateAll: true},
	).Create(&tickers)
	if result.Error != nil {
		return result.Error
	}
	return nil
}
