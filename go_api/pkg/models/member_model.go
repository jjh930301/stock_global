package models

import (
	"github.com/jjh930301/needsss_global/pkg/constant"
	"github.com/jjh930301/needsss_global/pkg/enum"
	"gorm.io/gorm"
)

type MemberModel struct {
	BaseModel
	Id        uint                `gorm:"primaryKey;autoIncrement"`
	AccountId string              `gorm:"uniqueIndex:idx_members_deleted_at_account_id;column:account_id;type:varchar(64);not null"`
	Password  string              `gorm:"column:password;type:varchar(128);not null"`
	Type      enum.MemberTypeEnum `gorm:"column:type;type:tinyint;default 1"`
	DeletedAt gorm.DeletedAt      `gorm:"uniqueIndex:idx_members_deleted_at_account_id"`
}

func (MemberModel) TableName() string {
	return constant.Member
}
