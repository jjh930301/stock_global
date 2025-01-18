package tickerservice

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"strings"

	"github.com/jjh930301/needsss_global/pkg/models"
	"github.com/jjh930301/needsss_global/pkg/repositories"
	"github.com/jjh930301/needsss_global/pkg/structs"
	"github.com/shopspring/decimal"
)

func GetKrTickers() error {
	data := "bld=dbms/MDC/STAT/standard/MDCSTAT01501&locale=ko_KR&mktId=ALL&trdDd=20240113&share=1&money=1&csvxls_isNo=false"

	req, err := http.NewRequest("POST", "http://data.krx.co.kr/comm/bldAttendant/getJsonData.cmd", bytes.NewBuffer([]byte(data)))
	if err != nil {
		return fmt.Errorf("error creating request: %v", err)
	}

	// headers
	req.Header.Set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
	req.Header.Set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
	req.Header.Set("Referer", "http://data.krx.co.kr/contents/MDC/MDI/mdiLoader/index.cmd?menuId=MDC0201020201")
	req.Header.Set("Cookie", "__smVisitorID=G021nW_fBlQ; JSESSIONID=1C1n83sJW2bI6LMKfkuEwiAI2R0ges16ismqA81Aaga4KpYivGBu4ToHn5e2Qt6e.bWRjX2RvbWFpbi9tZGNvd2FwMS1tZGNhcHAwMQ==")

	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		return fmt.Errorf("error sending request: %v", err)
	}
	defer resp.Body.Close()

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return fmt.Errorf("error reading response body: %v", err)
	}

	var response structs.KrTickerResponse
	err = json.Unmarshal(body, &response)
	if err != nil {
		return fmt.Errorf("error unmarshalling JSON response: %v", err)
	}

	err = bulkInsert(response.KrTicker)
	if err != nil {
		return err
	}
	return nil
}

func bulkInsert(tickers []structs.KrTicker) error {
	var krTickers []models.KrTickerModel
	for _, ticker := range tickers {
		if ticker.Market == "KOSPI" || ticker.Market == "KOSDAQ" {
			var market uint8
			if ticker.Market == "KOSPI" {
				market = 1
			} else {
				market = 2
			}
			value := strings.ReplaceAll(ticker.MarketCap, ",", "")
			marketCap, _ := decimal.NewFromString(value)
			krTicker := models.KrTickerModel{
				Market:    market,
				Symbol:    ticker.Symbol,
				Name:      ticker.Name,
				MarketCap: marketCap,
			}
			krTickers = append(krTickers, krTicker)
		}
	}
	err := repositories.KrTickerRepository{}.BulkDuplicatKeyInsert(krTickers)
	if err != nil {
		return err
	}
	return nil
}
