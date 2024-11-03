(./gradlew :core:build -x test --continuous) & \n
(./gradlew build -x test --continuous) & \n 
./gradlew api:bootRun -Dspring.profiles.active=dev