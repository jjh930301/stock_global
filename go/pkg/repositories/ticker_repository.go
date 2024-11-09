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

func (t TickerRepository) FindAll() []models.TickerModel {
	var tickers []models.TickerModel
	db.Database.Model(&models.TickerModel{}).Find(&tickers)
	return tickers
}

func (t TickerRepository) BulkDuplicateKeyInsert(stocks []structs.StockInfo) error {
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
		if stock.TradeStopType != nil {
			ticker.StopCode = stock.TradeStopType.Code
			ticker.StopName = stock.TradeStopType.Name
			ticker.StopText = stock.TradeStopType.Text
		}
		tickers = append(tickers, ticker)
	}
	result := db.Database.Model(&models.TickerModel{}).Clauses(
		clause.OnConflict{UpdateAll: true},
	).Create(&tickers)
	fmt.Println("affected rows ", int(result.RowsAffected))
	if result.Error != nil {
		return result.Error
	}
	return nil
}
