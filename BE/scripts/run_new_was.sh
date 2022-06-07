#!/bin/bash

CURRENT_PORT=$(cat /home/ec2-user/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

USER_NAME=$(env | grep username | cut -c 10-14)
PASSWORD=$(env | grep password | cut -c 10-17)
DATASOURCE=$(env | grep datasource | cut -c 12-97)
GITHUB_CLIENT_SECRET=$(env | grep GITHUB_CLIENT_SECRET | cut -c 22-61)
GITHUB_CLIENT_ID=$(env | grep GITHUB_CLIENT_ID | cut -c 18-37)
KAKAO_CLIENT_ID=$(env | grep KAKAO_CLIENT_ID | cut -c 17-48)

echo "> Current port of running WAS is ${CURRENT_PORT}."

if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "> No WAS is connected to nginx"
fi

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi

nohup java -jar -Dserver.port=${TARGET_PORT} /home/ec2-user/airbnb-deploy/build/libs/* --username=${USER_NAME} --password=${PASSWORD} --datasource=${DATASOURCE} --KAKAO_CLIENT_ID=${KAKAO_CLIENT_ID} --GITHUB_CLIENT_ID=${GITHUB_CLIENT_ID} --GITHUB_CLIENT_SECRET=${GITHUB_CLIENT_SECRET} > /home/ec2-user/nohup.out 2>&1 &
echo "> Now new WAS runs at ${TARGET_PORT}."
exit 0

