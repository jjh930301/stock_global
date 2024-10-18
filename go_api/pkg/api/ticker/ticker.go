package ticker

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/jjh930301/needsss_common/res"
	"github.com/jjh930301/needsss_global/pkg/constant"
	"github.com/jjh930301/needsss_global/pkg/repositories"

	"github.com/jjh930301/needsss_global/pkg/structs"
	"golang.org/x/net/proxy"
)

const firstPage = 1
const size = 100

// @Summary	ticker test
// @Description	test
// @Accept json
// @Produce	json
// @Router /ticker [get]
func GetTickers(c *gin.Context) {
	// symbols := []string{"AAPL"}

	total := getTickerAndInsert(firstPage)
	// 두번째 페이지부터 갖오면 됨
	for page := firstPage + 1; page <= total+1; page++ {
		go getTickerAndInsert(page)
	}

	res.Ok(
		c,
		"getting data",
		gin.H{"result": true},
		200,
	)
}

func getTickerAndInsert(page int) int {
	url := fmt.Sprintf(constant.TickerUri, page, size)
	// url := fmt.Sprintf("https://api.needsss.com/api1/interest?page=%d&ㅋㅋoffset=%d", page, size)
	dialer, err := proxy.SOCKS5("tcp", constant.TorProxy, nil, proxy.Direct)
	if err != nil {
		log.Fatalf("setting tor proxy is failure%v", err)
	}
	transport := &http.Transport{
		Dial: dialer.Dial,
	}

	client := &http.Client{
		Transport: transport,
	}
	resp, err := client.Get(url)
	if err != nil {
		fmt.Println("cannot get ticker list")
	}
	defer resp.Body.Close()

	var stockResponse structs.StockResponse
	// var stockResponse json.RawMessage
	if err := json.NewDecoder(resp.Body).Decode(&stockResponse); err != nil {
		fmt.Println(err)
	}
	err = repositories.TickerRepository{}.BulkDuplicateKeyInsert(stockResponse.Stocks)
	if err != nil {
		fmt.Println(err)
	}
	return stockResponse.TotalCount / size
}

// @Summary	ticker test
// @Description	test
// @Accept json
// @Produce	json
// @Success	200	{string} string "ok"
// @Router /ticker/chart [get]
func GetTickerChart(c *gin.Context) {
	// https://api.stock.naver.com/chart/foreign/item/NFLX.O/day?&stockExchangeType=NASDAQ&startDateTime=20240101&endDateTime=20240104
	tickers := repositories.TickerRepository{}.FindAll()
	res.Ok(
		c,
		"message",
		tickers,
		200,
	)
}
