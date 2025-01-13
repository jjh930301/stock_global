package models

import (
	"time"

	"github.com/jjh930301/needsss_global/pkg/constant"
)

type MemberHistoryModel struct {
	Ip        string      `gorm:"primaryKey;column:ip;type:varchar(32)"`
	Id        uint        `gorm:"primaryKey;column:id;type:bigint unsigned;not null"`
	Member    MemberModel `gorm:"-"`
	CreatedAt time.Time   `gorm:"primaryKey;type:datetime;autoCreateTime;column:created_at;not null;default:CURRENT_TIMESTAMP"`
}

func (MemberHistoryModel) TableName() string {
	return constant.MemberHistory
}
