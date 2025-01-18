package structs

type KrTicker struct {
	Symbol    string `json:"ISU_SRT_CD"` // symbol
	Name      string `json:"ISU_ABBRV"`  // name
	Market    string `json:"MKT_NM"`     // market
	MarketCap string `json:"LIST_SHRS"`  // marketCap
}

type KrTickerResponse struct {
	KrTicker        []KrTicker `json:"OutBlock_1"`
	CurrentDatetime string     `json:"CURRENT_DATETIME"`
}
