#!/bin/bash


echo "Compiling the project with maven"
mvn clean install

echo "Running unit tests"
mvn test

echo "Creating docker image"
docker build -t loans-app:latest .

echo "Apply yaml file"
docker compose  -f loans-app.yaml up -d

echo "Deployment success."