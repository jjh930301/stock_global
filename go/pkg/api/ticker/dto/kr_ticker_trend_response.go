package tickerDto

type CompareToPreviousPrice struct {
	Code string `json:"code"`
	Text string `json:"text"`
	Name string `json:"name"`
}

type KrTickerTrendResponse struct {
	Symbol                      string                 `json:"itemCode"`
	BizDate                     string                 `json:"bizdate"`
	Foreign                     string                 `json:"foreignerPureBuyQuant"`  // 외국인
	ForeignRatio                string                 `json:"foreignerHoldRatio"`     // 외국인 보유율
	Organ                       string                 `json:"organPureBuyQuant"`      // 기관
	Individual                  string                 `json:"individualPureBuyQuant"` // 개인
	ClosePrice                  string                 `json:"closePrice"`
	CompareToPreviousClosePrice string                 `json:"compareToPreviousClosePrice"`
	CompareToPreviousPrice      CompareToPreviousPrice `json:"compareToPreviousPrice"`
	AccumulatedTradingVolume    string                 `json:"accumulatedTradingVolume"`
}
