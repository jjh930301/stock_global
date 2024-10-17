package repositories

import (
	"fmt"
	"strings"

	"github.com/jjh930301/needsss_global/pkg/db"
	"github.com/jjh930301/needsss_global/pkg/models"
	"github.com/jjh930301/needsss_global/pkg/structs"
	"github.com/shopspring/decimal"
	"gorm.io/gorm/clause"
)

type TickerRepository struct{}

func (t TickerRepository) BulkInsert(stocks []structs.StockInfo) error {
	var tickers []models.TickerModel
	for _, stock := range stocks {
		value := strings.ReplaceAll(stock.MarketValue, ",", "")
		marketValue, _ := decimal.NewFromString(value)
		ticker := models.TickerModel{
			Symbol:       stock.SymbolCode,
			StockType:    stock.StockType,
			NationType:   stock.NationType,
			ReutersCode:  stock.ReutersCode,
			Name:         stock.StockNameEng,
			KoName:       stock.StockName,
			MarketStatus: stock.MarketStatus,
			MarketValue:  marketValue,
		}
		if stock.StockExchangeType != nil {
			ticker.StartTime = stock.StockExchangeType.StartTime
			ticker.EndTime = stock.StockExchangeType.EndTime
			ticker.NationCode = stock.StockExchangeType.NationCode
			ticker.MarKetType = stock.StockExchangeType.Name
		}
		tickers = append(tickers, ticker)
	}
	result := db.Database.Clauses(
		clause.OnConflict{UpdateAll: true},
	).Create(&tickers)
	fmt.Println("affected rows ", int(result.RowsAffected))
	if result.Error != nil {
		return result.Error
	}
	return nil
}
