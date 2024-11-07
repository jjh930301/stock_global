package tickerservice

import (
	"encoding/json"
	"fmt"
	"os"

	"github.com/jjh930301/needsss_global/pkg/repositories"
	"github.com/jjh930301/needsss_global/pkg/structs"
	"github.com/jjh930301/needsss_global/pkg/utils"
)

const size = 100

func GetTickerAndInsert(page int) (int, error) {
	url := fmt.Sprintf(os.Getenv("TICKER_URL"), page, size)

	client := utils.TorClient()
	resp, err := client.Get(url)
	if err != nil {
		fmt.Println("cannot get ticker list")
		return 0, err
	}
	defer resp.Body.Close()

	var stockRes structs.StockResponse
	// var stockRes json.RawMessage
	if err := json.NewDecoder(resp.Body).Decode(&stockRes); err != nil {
		fmt.Println(err)
	}
	err = repositories.TickerRepository{}.BulkDuplicateKeyInsert(stockRes.Stocks)
	if err != nil {
		fmt.Println(err)
	}
	return stockRes.TotalCount / size, nil
}
