package models

import (
	"github.com/jjh930301/needsss_global/pkg/constant"
	"github.com/jjh930301/needsss_global/pkg/enum"
	"gorm.io/gorm"
)

type MemberModel struct {
	BaseModel
	Id         uint                `gorm:"primaryKey;autoIncrement"`
	AccountId  string              `gorm:"uniqueIndex:idx_members_account_id;column:account_id;type:varchar(64);not null"`
	Password   *string             `gorm:"column:password;type:varchar(128);default:null"`
	Type       enum.MemberTypeEnum `gorm:"column:type;type:tinyint;default:1"`
	AccessedBy uint                `gorm:"column:accessed_by;type:int;default:null"`
	DeletedAt  gorm.DeletedAt      `gorm:"column:deleted_at"`
}

func (MemberModel) TableName() string {
	return constant.Member
}
