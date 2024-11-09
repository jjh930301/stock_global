package models

import (
	"time"
)

type BaseModel struct {
	CreatedAt time.Time `gorm:"type:datetime;autoCreateTime;column:created_at;not null;default:CURRENT_TIMESTAMP"`
	UpdatedAt time.Time `gorm:"type:datetime;autoUpdateTime;column:updated_at;default:CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"`
}
