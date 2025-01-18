package repositories

import (
	"github.com/jjh930301/needsss_global/pkg/db"
	"github.com/jjh930301/needsss_global/pkg/models"
	"gorm.io/gorm/clause"
)

type KrTickerRepository struct{}

func (k KrTickerRepository) FindAll() []models.KrTickerModel {
	var krTickers []models.KrTickerModel
	db.Database.Model(
		&models.KrTickerModel{},
	).Select("symbol").Find(&krTickers)
	return krTickers
}

func (k KrTickerRepository) BulkDuplicatKeyInsert(tickers []models.KrTickerModel) error {
	result := db.Database.Model(&models.KrTickerModel{}).Clauses(
		clause.OnConflict{UpdateAll: true},
	).Create(&tickers)
	if result.Error != nil {
		return result.Error
	}
	return nil
}
