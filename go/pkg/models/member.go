package models

import (
	"github.com/jjh930301/stock_global/pkg/constant"
	"github.com/jjh930301/stock_global/pkg/enum"
	"gorm.io/gorm"
)

type Member struct {
	BaseModel
	Id         uint                `gorm:"primaryKey;autoIncrement;type:bigint unsigned"`
	AccountId  string              `gorm:"uniqueIndex:idx_members_account_id;column:account_id;type:varchar(64);not null"`
	Password   *string             `gorm:"column:password;type:varchar(128);default:null"`
	Type       enum.MemberTypeEnum `gorm:"column:type;type:tinyint;default:1"`
	AccessedBy uint                `gorm:"column:accessed_by;type:int;default:null"`
	DeletedAt  gorm.DeletedAt      `gorm:"column:deleted_at"`
	Histories  []MemberHistory     `gorm:"foreignKey:Id;references:Id"`
}

func (Member) TableName() string {
	return constant.Member
}
