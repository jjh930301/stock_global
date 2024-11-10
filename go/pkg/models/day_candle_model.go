package models

import (
	"time"

	"github.com/jjh930301/needsss_global/pkg/constant"
	"github.com/shopspring/decimal"
)

type DayCandleModel struct {
	Symbol string          `gorm:"column:symbol;type:varchar(5);primary_key" json:"symbol,omitempty"`
	Ticker TickerModel     `gorm:"column:symbol;type:varchar(5);foreignKey:Symbol;references:Symbol" json:"ticker,omitempty"`
	Date   time.Time       `gorm:"column:date;type:date;primary_key;index:idx_day_candles_date;" json:"date,omitempty"`
	Open   decimal.Decimal `gorm:"column:open;type:decimal(10,3)" json:"open,omitempty"`
	High   decimal.Decimal `gorm:"column:high;type:decimal(10,3)" json:"high,omitempty"`
	Low    decimal.Decimal `gorm:"column:low;type:decimal(10,3)" json:"low,omitempty"`
	Close  decimal.Decimal `gorm:"column:close;type:decimal(10,3)" json:"close,omitempty"`
	Volume int64           `gorm:"column:volume;type:bigint;not null" json:"volume,omitempty"`
}

func (DayCandleModel) TableName() string {
	return constant.DayCandle
}
