#!/bin/bash

cd Mini-Dooray-8-account
./mvnw clean package
cd ../Mini-Dooray-8-task
./mvnw clean package
cd ..
./mvnw clean package
docker-compose build
