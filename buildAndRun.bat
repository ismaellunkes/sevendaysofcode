@echo off
call mvn clean package
call docker build -t org.example/sevendaysofcode .
call docker rm -f sevendaysofcode
call docker run -d -p 9080:9080 -p 9443:9443 --name sevendaysofcode org.example/sevendaysofcode