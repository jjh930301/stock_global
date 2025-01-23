package tickerservice

import (
	"encoding/json"
	"fmt"
	"os"
	"strings"

	tickerDto "github.com/jjh930301/needsss_global/pkg/api/ticker/dto"
	"github.com/jjh930301/needsss_global/pkg/models"
	"github.com/jjh930301/needsss_global/pkg/repositories"
	"github.com/jjh930301/needsss_global/pkg/utils"
	"github.com/shopspring/decimal"
)

const size = 100

func GetTicker(page int) (int, error) {
	url := fmt.Sprintf(os.Getenv("TICKER_URL"), page, size)

	client := utils.TorClient()
	resp, err := client.Get(url)
	if err != nil {
		fmt.Println("cannot get ticker list")
		return 0, err
	}
	defer resp.Body.Close()

	var stockRes tickerDto.TickerResponse
	if err := json.NewDecoder(resp.Body).Decode(&stockRes); err != nil {
		fmt.Println(err)
	}
	var tickers []models.Ticker
	for _, stock := range stockRes.Stocks {
		value := strings.ReplaceAll(stock.MarketValue, ",", "")

		marketValue, _ := decimal.NewFromString(value)
		ticker := models.Ticker{
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
	err = repositories.TickerRepository{}.BulkDuplicateKeyInsert(tickers)
	if err != nil {
		fmt.Println(err)
	}
	return stockRes.TotalCount / size, nil
}
