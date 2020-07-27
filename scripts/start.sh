#!/usr/bin/env bash

PROFILE_NAME=miracle-api
REPOSITORY=/home/ec2-user/app/$PROFILE_NAME

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

echo "> nohup java -jar
        -Dspring.config.location=classpath:/application.properties
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &"

nohup java -jar
    -Dspring.config.location=classpath:/application.properties
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
