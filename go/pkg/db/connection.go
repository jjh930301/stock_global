package db

import (
	"fmt"

	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

// var DB *gorm.DB

func InitDb(db *gorm.DB, user, password, host, database string) *gorm.DB {
	db = connection(user, password, host, database)
	return db
}

func connection(user, password, host, database string) *gorm.DB {
	uri := fmt.Sprintf(
		"%s:%s@tcp(%s:%d)/%s?charset=utf8&parseTime=true",
		user,
		password,
		host,
		3306,
		database,
	)
	db, err := gorm.Open(mysql.Open(uri), &gorm.Config{
		Logger: logger.Default.LogMode(logger.Silent),
	})
	if err != nil {
		fmt.Println("error:::", err)
		panic(err)
	}
	db.Set("gorm:table_options", "ENGINE=InnoDB")

	return db
}
