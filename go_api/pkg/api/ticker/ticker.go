package ticker

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
	"github.com/jjh930301/needsss_common/res"
	"github.com/jjh930301/needsss_global/pkg/constant"

	"github.com/jjh930301/needsss_global/pkg/structs"
	"golang.org/x/net/proxy"
)

// @Summary	ticker test
// @Description	test
// @Accept json
// @Produce	json
// @Param page query int true "page"
// @Param size query int true "size"
// @Success	200	{string} string "ok"
// @Router /ticker [get]
func GetTickers(c *gin.Context) {
	// symbols := []string{"AAPL"}
	p := c.Request.URL.Query().Get("page")
	page, err := strconv.Atoi(p)
	if err != nil {
		res.BadRequest(c, "required page", 400)
		return
	}
	s := c.Request.URL.Query().Get("size")
	size, err := strconv.Atoi(s)
	if err != nil {
		res.BadRequest(c, "required size", 400)
		return
	}
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
		res.BadRequest(c, "failure request", 500)
		return
	}
	defer resp.Body.Close()
	var stockResponse structs.StockResponse
	// var stockResponse json.RawMessage
	if err := json.NewDecoder(resp.Body).Decode(&stockResponse); err != nil {
		fmt.Println(err)
		res.ServerError(c)
		return
	}
	// start := "2023-01-01"
	// end := "2024-12-31"
	// startDate, _ := time.Parse("2006-01-02", start)
	// endDate, _ := time.Parse("2006-01-02", end)

	res.Ok(
		c,
		"getting data",
		stockResponse,
		200,
	)
}

// @Summary	ticker test
// @Description	test
// @Accept json
// @Produce	json
// @Param symbol path string true "page"
// @Success	200	{string} string "ok"
// @Router /ticker/chart/{symbol} [get]
func GetTickerChart(c *gin.Context) {
	// symbols := []string{"AAPL"}
	// https://api.stock.naver.com/chart/foreign/item/NFLX.O/day?&stockExchangeType=NASDAQ&startDateTime=20240101&endDateTime=20240104

	res.Ok(
		c,
		"message",
		"ok",
		200,
	)
}
