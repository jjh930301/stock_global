package models

import (
	"github.com/jjh930301/needsss_global/pkg/constant"
	"github.com/shopspring/decimal"
)

type TickerModel struct {
	Symbol       string          `gorm:"column:symbol;type:varchar(12);not null;primary_key;comment:ticker symbol" json:"symbol,omitempty"`
	StockType    string          `gorm:"column:stock_type;type:varchar(20);default null;comment:check stock type" json:"stockType"`
	Name         string          `gorm:"column:name;type:varchar(100);default null;comment:name" json:"name"`
	KoName       string          `gorm:"column:ko_name;type:varchar(100);default null;comment:korea name" json:"koName"`
	NationType   string          `gorm:"column:nation_type;type:varchar(12)" json:"nationType"`
	NationCode   string          `gorm:"column:nation_code;type:varchar(12)" json:"nationCode"`              // StockExchangeInfo.NationCode
	StartTime    string          `gorm:"column:start_time;type:varchar(20);default null" json:"startTime"`   // StockExchangeInfo.StartTime
	EndTime      string          `gorm:"column:end_time;type:varchar(20);default null" json:"endTime"`       // StockExchangeInfo.EndTime
	MarKetType   string          `gorm:"column:market_type;type:varchar(20);default null" json:"marketType"` // StockExchangeInfo.Name
	ReutersCode  string          `gorm:"column:reuters_code;type:varchar(20);default null" json:"reuterCode"`
	MarketStatus string          `gorm:"column:market_status;type:varchar(10);default null;comment:market status CLOSE | ?" json:"marketStatus"`
	MarketValue  decimal.Decimal `gorm:"column:market_value;type:decimal(11,2);default 0;comment:시가총액" json:"marketValue"`
	StopCode     string          `gorm:"column:stop_code;type:varchar(20);default null;" json:"stopCode"`
	StopText     string          `gorm:"column:stop_text;type:varchar(100);default null;" json:"stopText"`
	StopName     string          `gorm:"column:stop_name;type:varchar(100);default null;" json:"stopName"`
}

func (TickerModel) TableName() string {
	return constant.Ticker
}
