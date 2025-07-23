package models

import (
	"time"

	"github.com/jjh930301/stock_global/pkg/constant"
	"github.com/shopspring/decimal"
)

type IndexDayCandle struct {
	Market int16           `gorm:"column:market;type:tinyint;primary_key" json:"market,omitempty"`
	Date   time.Time       `gorm:"column:date;type:date;primary_key;index" json:"date,omitempty"`
	Open   decimal.Decimal `gorm:"column:open;type:decimal(11,3)" json:"open,omitempty"`
	High   decimal.Decimal `gorm:"column:high;type:decimal(11,3)" json:"high,omitempty"`
	Low    decimal.Decimal `gorm:"column:low;type:decimal(11,3)" json:"low,omitempty"`
	Close  decimal.Decimal `gorm:"column:close;type:decimal(11,3)" json:"close,omitempty"`
	Volume int64           `gorm:"column:volume;type:bigint;not null" json:"volume,omitempty"`
}

func (IndexDayCandle) TableName() string {
	return constant.IndexDayCandle
}
