package repositories

import (
	"github.com/jjh930301/stock_global/pkg/db"
	"github.com/jjh930301/stock_global/pkg/models"
	"gorm.io/gorm/clause"
)

type TickerRepository struct{}

func (t TickerRepository) FindAll() []models.Ticker {
	var tickers []models.Ticker
	db.Database.Model(&models.Ticker{}).Order("symbol asc").Find(&tickers)
	return tickers
}

func (t TickerRepository) BulkDuplicateKeyInsert(tickers []models.Ticker) error {
	result := db.Database.Model(&models.Ticker{}).Clauses(
		clause.OnConflict{UpdateAll: true},
	).Create(&tickers)
	if result.Error != nil {
		return result.Error
	}
	return nil
}
