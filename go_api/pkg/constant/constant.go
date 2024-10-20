package constant

const (
	TorProxy       = "127.0.0.1:9050"
	TickerUri      = "https://api.stock.naver.com/stock/exchange/NASDAQ/marketValue?page=%d&pageSize=%d"
	TickerChartUri = "https://api.stock.naver.com/chart/foreign/item/%s/day?&stockExchangeType=NASDAQ&startDateTime=%s&endDateTime=%s"
)
