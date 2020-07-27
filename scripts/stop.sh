#!/usr/bin/env bash

PORT=8080

echo "> $PORT 에서 구동 중인 애플리케이션 pid 확인"
PID=$(lsof -ti tcp:${PORT})

if [ -z ${PID} ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $PID"
  kill -15 ${PID}
  sleep 5
fi
