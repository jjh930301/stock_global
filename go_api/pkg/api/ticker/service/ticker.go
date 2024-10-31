package tickerservice

import (
	"encoding/json"
	"fmt"

	"github.com/jjh930301/needsss_global/pkg/constant"
	"github.com/jjh930301/needsss_global/pkg/repositories"
	"github.com/jjh930301/needsss_global/pkg/structs"
	"github.com/jjh930301/needsss_global/pkg/utils"
)

const size = 100

func GetTickerAndInsert(page int) int {
	url := fmt.Sprintf(constant.TickerUri, page, size)

	client := utils.TorClient()
	resp, err := client.Get(url)
	if err != nil {
		fmt.Println("cannot get ticker list")
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
	return stockRes.TotalCount / size
}
