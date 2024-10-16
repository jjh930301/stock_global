package model

import "time"

// 전체 응답 구조체
type StockResponse struct {
	Page       int         `json:"page"`
	PageSize   int         `json:"pageSize"`
	TotalCount int         `json:"totalCount"`
	Stocks     []StockInfo `json:"stocks"`
}

// 개별 주식 정보 구조체
type StockInfo struct {
	StockType              string            `json:"stockType"`
	StockEndType           string            `json:"stockEndType"`
	CompareToPreviousPrice ComparePriceInfo  `json:"compareToPreviousPrice"`
	NationType             string            `json:"nationType"`
	StockExchangeType      StockExchangeInfo `json:"stockExchangeType"`
	ReutersCode            string            `json:"reutersCode"`  // api path에 사용될 코드
	SymbolCode             string            `json:"symbolCode"`   // 종목 코드
	StockName              string            `json:"stockName"`    // 종목 이름 한글
	StockNameEng           string            `json:"stockNameEng"` // 종목 이름 영어
	ReutersIndustryCode    string            `json:"reutersIndustryCode"`
	IndustryCodeType       IndustryInfo      `json:"industryCodeType"`
	OpenPrice              string            `json:"openPrice"`
	ClosePrice             string            `json:"closePrice"`
	CompareToPreviousClose string            `json:"compareToPreviousClosePrice"`
	FluctuationsRatio      string            `json:"fluctuationsRatio"`
	ExecutedVolume         *float64          `json:"executedVolume"` // Nullable
	TradingVolume          string            `json:"accumulatedTradingVolume"`
	TradingValue           string            `json:"accumulatedTradingValue"`
	TradingValueKrw        string            `json:"accumulatedTradingValueKrwHangeul"`
	LocalTradedAt          time.Time         `json:"localTradedAt"`
	MarketStatus           string            `json:"marketStatus"`        // CLOSE시 값을 historical 데이터를 받으면 안됨
	OverMarketPriceInfo    *string           `json:"overMarketPriceInfo"` // Nullable
	MarketValue            string            `json:"marketValue"`
	MarketValueHangeul     string            `json:"marketValueHangeul"`
	MarketValueKrwHangeul  string            `json:"marketValueKrwHangeul"`
	CurrencyType           CurrencyInfo      `json:"currencyType"`
	Dividend               string            `json:"dividend"`
	DividendPayAt          time.Time         `json:"dividendPayAt"`
	TradeStopType          TradeStopInfo     `json:"tradeStopType"`
	EndUrl                 string            `json:"endUrl"`
	DelayTime              int               `json:"delayTime"`
	DelayTimeName          string            `json:"delayTimeName"`
	ExchangeOperatingTime  bool              `json:"exchangeOperatingTime"`
	StockEndUrl            string            `json:"stockEndUrl"`
}

// 가격 비교 정보 구조체
type ComparePriceInfo struct {
	Code string `json:"code"`
	Text string `json:"text"`
	Name string `json:"name"`
}

// 주식 거래소 정보 구조체
type StockExchangeInfo struct {
	Code               string `json:"code"`
	ZoneId             string `json:"zoneId"`
	NationType         string `json:"nationType"`
	DelayTime          int    `json:"delayTime"`
	StartTime          string `json:"startTime"` // 장 시작 시간
	EndTime            string `json:"endTime"`   // 장 종료 시간
	ClosePriceSendTime string `json:"closePriceSendTime"`
	NameKor            string `json:"nameKor"`
	NameEng            string `json:"nameEng"`
	NationName         string `json:"nationName"`
	StockType          string `json:"stockType"`
	NationCode         string `json:"nationCode"` // 국가 코드
	Name               string `json:"name"`       // 마켓 타입
}

// 산업 코드 정보 구조체
type IndustryInfo struct {
	Code             string `json:"code"`
	IndustryGroupKor string `json:"industryGroupKor"`
	Name             string `json:"name"`
}

// 통화 정보 구조체
type CurrencyInfo struct {
	Code string `json:"code"`
	Text string `json:"text"`
	Name string `json:"name"`
}

// 거래 정지 정보 구조체
type TradeStopInfo struct {
	Code string `json:"code"`
	Text string `json:"text"`
	Name string `json:"name"`
}
