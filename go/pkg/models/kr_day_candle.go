package models

import (
	"time"

	"github.com/jjh930301/needsss_global/pkg/constant"
	"github.com/shopspring/decimal"
)

type KrDayCandle struct {
	Symbol   string          `gorm:"column:symbol;type:varchar(6);primary_key" json:"symbol,omitempty"`
	KrTicker KrTicker        `gorm:"column:symbol;type:varchar(6);foreignKey:Symbol;references:Symbol" json:"ticker,omitempty"`
	Date     time.Time       `gorm:"column:date;type:date;primary_key;index" json:"date,omitempty"`
	Open     decimal.Decimal `gorm:"column:open;type:decimal(11,3)" json:"open,omitempty"`
	High     decimal.Decimal `gorm:"column:high;type:decimal(11,3)" json:"high,omitempty"`
	Low      decimal.Decimal `gorm:"column:low;type:decimal(11,3)" json:"low,omitempty"`
	Close    decimal.Decimal `gorm:"column:close;type:decimal(11,3)" json:"close,omitempty"`
	Volume   int64           `gorm:"column:volume;type:bigint;not null" json:"volume,omitempty"`
	Percent  float32         `gorm:"column:percent;type:float;not null" json:"percent,omitempty"`
}

func (KrDayCandle) TableName() string {
	return constant.KrDayCandle
}
