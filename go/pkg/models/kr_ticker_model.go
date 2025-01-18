package models

import (
	"github.com/jjh930301/needsss_global/pkg/constant"
	"github.com/shopspring/decimal"
)

type KrTickerModel struct {
	Symbol     string             `gorm:"column:symbol;type:varchar(6);not null;primary_key;comment:ticker symbol" json:"symbol"`
	Name       string             `gorm:"column:name;type:varchar(100);default null;comment:name" json:"name"`
	Market     uint8              `gorm:"column:market;type:tinyint unsigned;comment:1 : KOSPI , 2 : KOSDAQ" json:"market"`
	MarketCap  decimal.Decimal    `gorm:"column:market_cap;type:decimal(17,0) unsigned;default:null" json:"market_cap"`
	DayCandles []KrDayCandleModel `gorm:"foreignKey:Symbol;references:Symbol" json:"KrdayCandles,omitempty"`
}

func (KrTickerModel) TableName() string {
	return constant.KrTicker
}
