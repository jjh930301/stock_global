package repositories

import (
	"github.com/jjh930301/needsss_global/pkg/db"
	"github.com/jjh930301/needsss_global/pkg/models"
	"gorm.io/gorm/clause"
)

type TickerRepository struct{}

func (t TickerRepository) FindAll() []models.TickerModel {
	var tickers []models.TickerModel
	db.Database.Model(&models.TickerModel{}).Find(&tickers)
	return tickers
}

func (t TickerRepository) BulkDuplicateKeyInsert(tickers []models.TickerModel) error {
	result := db.Database.Model(&models.TickerModel{}).Clauses(
		clause.OnConflict{UpdateAll: true},
	).Create(&tickers)
	if result.Error != nil {
		return result.Error
	}
	return nil
}
