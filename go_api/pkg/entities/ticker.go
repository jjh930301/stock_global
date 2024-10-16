package entities

type Ticker struct {
    Symbol  string `gorm:"column:symbol;type:varchar(12);not null;primary_key" json:"symbol,omitempty"`
    Name    string `gorm:"column:name;type:varchar(100);default null"`
    KoName  string `gorm:"column:ko_name;type:varchar(100);default null"`
    Nation  string `gorm:"column:nation;type:varchar(12)" json:"nation"`
}