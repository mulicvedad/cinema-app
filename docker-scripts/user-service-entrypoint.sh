#!/bin/sh

while ! nc -z eureka-service 8761 ; do
    echo "Waiting for Eureka server"
    sleep 3
done