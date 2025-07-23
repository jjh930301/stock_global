package models

import (
	"time"

	"github.com/jjh930301/stock_global/pkg/constant"
	"github.com/shopspring/decimal"
)

type KrTrend struct {
	Symbol       string          `gorm:"column:symbol;type:varchar(6);primary_key" json:"symbol,omitempty"`
	KrTicker     KrTicker        `gorm:"column:symbol;type:varchar(6);foreignKey:Symbol;references:Symbol" json:"ticker,omitempty"`
	Date         time.Time       `gorm:"column:date;type:date;primary_key;index" json:"date,omitempty"`
	Organ        int             `gorm:"column:organ;type:int;not null" json:"organ,omitempty"`
	Foreign      int             `gorm:"column:foreign;type:int;not null" json:"foreign,omitempty"`
	ForeignRatio decimal.Decimal `gorm:"column:foreign_ratio;type:decimal(7,2)" json:"foreignRatio,omitempty"`
	Individual   int             `gorm:"column:individual;type:int;not null" json:"individual,omitempty"`
}

func (KrTrend) TableName() string {
	return constant.KrTrend
}
