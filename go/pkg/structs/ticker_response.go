package structs

import "time"

// 전체 응답
type TickerResponse struct {
	Page       int          `json:"page"`
	PageSize   int          `json:"pageSize"`
	TotalCount int          `json:"totalCount"`
	Stocks     []TickerInfo `json:"stocks"`
}

// 개별 주식 정보
type TickerInfo struct {
	StockType              string             `json:"stockType"`    // save
	StockEndType           string             `json:"stockEndType"` //save
	CompareToPreviousPrice ComparePriceInfo   `json:"compareToPreviousPrice"`
	NationType             string             `json:"nationType"`        //save
	StockExchangeType      *StockExchangeInfo `json:"stockExchangeType"` // save null check 후
	ReutersCode            string             `json:"reutersCode"`       // save api path에 사용될 코드
	SymbolCode             string             `json:"symbolCode"`        // save 종목 코드
	StockName              string             `json:"stockName"`         // save 종목 이름 한글
	StockNameEng           string             `json:"stockNameEng"`      // save 종목 이름 영어
	ReutersIndustryCode    string             `json:"reutersIndustryCode"`
	IndustryCodeType       IndustryInfo       `json:"industryCodeType"`
	OpenPrice              string             `json:"openPrice"`
	ClosePrice             string             `json:"closePrice"`
	CompareToPreviousClose string             `json:"compareToPreviousClosePrice"`
	FluctuationsRatio      string             `json:"fluctuationsRatio"`
	ExecutedVolume         *float64           `json:"executedVolume"`
	TradingVolume          string             `json:"accumulatedTradingVolume"`
	TradingValue           string             `json:"accumulatedTradingValue"` // candle 해당일 거래 대금
	TradingValueKrw        string             `json:"accumulatedTradingValueKrwHangeul"`
	LocalTradedAt          time.Time          `json:"localTradedAt"`       // candle 거래일
	MarketStatus           string             `json:"marketStatus"`        // save CLOSE시 값을 historical 데이터를 받으면 안됨
	OverMarketPriceInfo    *OverMarketInfo    `json:"overMarketPriceInfo"` // Nullable
	MarketValue            string             `json:"marketValue"`         // save 시가총액
	MarketValueHangeul     string             `json:"marketValueHangeul"`
	MarketValueKrwHangeul  string             `json:"marketValueKrwHangeul"`
	CurrencyType           CurrencyInfo       `json:"currencyType"`
	Dividend               string             `json:"dividend"`
	DividendPayAt          time.Time          `json:"dividendPayAt"`
	TradeStopType          *TradeStopInfo     `json:"tradeStopType"`
	EndUrl                 string             `json:"endUrl"`
	DelayTime              int                `json:"delayTime"`
	DelayTimeName          string             `json:"delayTimeName"`
	ExchangeOperatingTime  bool               `json:"exchangeOperatingTime"`
	StockEndUrl            string             `json:"stockEndUrl"`
}

// 연장 거래 정보
type OverMarketInfo struct {
	CompareToPreviousClosePrice string           `json:"compareToPreviousClosePrice"`
	CompareToPreviousPrice      ComparePriceInfo `json:"compareToPreviousPrice"`
	FluctuationsRatio           string           `json:"fluctuationsRatio"`
	LocalTradedAt               time.Time        `json:"localTradedAt"`
	OverMarketStatus            string           `json:"overMarketStatus"`
	OverPrice                   string           `json:"overPrice"`
	TradingSessionType          string           `json:"tradingSessionType"`
}

// 가격 비교 정보
type ComparePriceInfo struct {
	Code string `json:"code"`
	Text string `json:"text"`
	Name string `json:"name"`
}

// 주식 거래소 정보
type StockExchangeInfo struct {
	Code               string `json:"code"`
	ZoneId             string `json:"zoneId"`
	NationType         string `json:"nationType"`
	DelayTime          int    `json:"delayTime"`
	StartTime          string `json:"startTime"` // 장 시작 시간 save
	EndTime            string `json:"endTime"`   // 장 종료 시간 save
	ClosePriceSendTime string `json:"closePriceSendTime"`
	NameKor            string `json:"nameKor"`
	NameEng            string `json:"nameEng"`
	NationName         string `json:"nationName"`
	StockType          string `json:"stockType"`
	NationCode         string `json:"nationCode"` // 국가 코드
	Name               string `json:"name"`       // save 마켓 타입
}

// 산업 코드 정보
type IndustryInfo struct {
	Code             string `json:"code"`
	IndustryGroupKor string `json:"industryGroupKor"`
	Name             string `json:"name"`
}

// 통화 정보
type CurrencyInfo struct {
	Code string `json:"code"` // save 화폐 단위 insert 후 확인
	Text string `json:"text"`
	Name string `json:"name"`
}

// 거래 정지 정보
type TradeStopInfo struct {
	Code string `json:"code"` // 값을 그대로 담아서 넣어서 확인
	Text string `json:"text"` // 값을 그대로 담아서 확인
	Name string `json:"name"` // 값을 그대로 담아서 확인
}
