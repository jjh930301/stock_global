package tickerservice

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"net/url"
	"os"
	"strconv"
	"strings"
	"sync"
	"time"

	tickerDto "github.com/jjh930301/needsss_global/pkg/api/ticker/dto"
	"github.com/jjh930301/needsss_global/pkg/models"
	"github.com/jjh930301/needsss_global/pkg/repositories"
	"github.com/jjh930301/needsss_global/pkg/utils"
	"github.com/shopspring/decimal"
)

func GetKrTickers() error {
	dateStr := time.Now().Format("20060102")

	// URL-encoded form data 생성
	data := url.Values{}
	data.Set("bld", "dbms/MDC/STAT/standard/MDCSTAT01501")
	data.Set("locale", "ko_KR")
	data.Set("mktId", "ALL")
	data.Set("trdDd", dateStr)
	data.Set("share", "1")
	data.Set("money", "1")
	data.Set("csvxls_isNo", "false")

	req, err := http.NewRequest("POST", "http://data.krx.co.kr/comm/bldAttendant/getJsonData.cmd", strings.NewReader(data.Encode()))
	if err != nil {
		return fmt.Errorf("error creating request: %v", err)
	}

	// headers
	req.Header.Set("Origin", "http://data.krx.co.kr")
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

	var response tickerDto.KrTickerResponse
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

func bulkInsert(tickers []tickerDto.KrTicker) error {
	var krTickers []models.KrTicker
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
			krTicker := models.KrTicker{
				Market:    market,
				Symbol:    ticker.Symbol,
				Name:      ticker.Name,
				MarketCap: marketCap,
			}
			krTickers = append(krTickers, krTicker)
		}
	}
	err := repositories.KrTickerRepository{}.BulkDuplicateKeyInsert(krTickers)
	if err != nil {
		return err
	}
	return nil
}

// required chang logic
func GetKrTickerTrends() error {
	krTickers := repositories.KrTickerRepository{}.FindAll()
	batchSize := 100
	for i := 0; i < len(krTickers); i += batchSize {
		end := i + batchSize
		if end > len(krTickers) {
			end = len(krTickers)
		}
		batch := krTickers[i:end]
		var innerWg sync.WaitGroup
		for _, krTicker := range batch {
			innerWg.Add(1)
			go func(t models.KrTicker) {
				defer innerWg.Done()
				fetchAndProcessTrendData(t.Symbol, "")
			}(krTicker)
		}
		innerWg.Wait()
	}

	return nil
}

func fetchAndProcessTrendData(ticker string, date string) {
	client := utils.TorClient() // Tor 클라이언트 사용
	pageSize := 50
	totalPages := 6
	baseURL := os.Getenv("KR_TREND_URL")

	if date == "" {
		date = time.Now().In(utils.KrTime()).Format("20060102")
	}

	var trends []models.KrTrend
	for page := 1; page <= totalPages; page++ {
		url := fmt.Sprintf(baseURL+"?pageSize=%d&bizdate=%s", ticker, pageSize, date)
		res, err := client.Get(url)
		if err != nil {
			fmt.Printf("Failed to fetch data for ticker %s on page %d: %v\n", ticker, page, err)
			return
		}
		defer res.Body.Close()

		body, err := io.ReadAll(res.Body)
		if err != nil {
			fmt.Printf("Failed to read response for ticker %s on page %d: %v\n", ticker, page, err)
			return
		}

		var krTrends []tickerDto.KrTickerTrendResponse
		err = json.Unmarshal(body, &krTrends)
		if err != nil {
			fmt.Printf("Failed to unmarshal response for ticker %s on page %d: %v\n", ticker, page, err)
			return
		}

		for _, trend := range krTrends {
			replacer := strings.NewReplacer(
				",", "",
				"+", "",
				"%", "",
			)
			dateTime, _ := time.Parse("20060102", trend.BizDate)
			foreign, _ := strconv.Atoi(replacer.Replace(trend.Foreign))
			organ, _ := strconv.Atoi(replacer.Replace(trend.Organ))
			individual, _ := strconv.Atoi(replacer.Replace(trend.Individual))
			replaceRatio := replacer.Replace(trend.ForeignRatio)
			ratioFloat, _ := strconv.ParseFloat(replaceRatio, 64)
			ratio := decimal.NewFromFloat(ratioFloat)
			krTrend := models.KrTrend{
				Symbol:       trend.Symbol,
				Date:         dateTime,
				Foreign:      foreign,
				ForeignRatio: ratio,
				Organ:        organ,
				Individual:   individual,
			}

			trends = append(trends, krTrend)
		}
		date = krTrends[len(krTrends)-1].BizDate
	}

	repositories.KrTrendRepository{}.BulkDuplicateKeyInsert(trends)
}
