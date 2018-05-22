# BeaverCoffee
School project for non-relational databases course. [![Build Status](https://travis-ci.com/Kalk88/BeaverCoffee.svg?branch=master)](https://travis-ci.com/Kalk88/BeaverCoffee)

## API documentation
Available on Apiary
[Docs](https://beavercoffe.docs.apiary.io/#)

## MongoDB container on Docker instructions
1. Install docker [quickstart](https://docs.docker.com/v1.11/engine/quickstart/)
2. docker pull mongo
3. docker run -d -p 127.0.0.1:27017:27017 --name beaverDB -d mongo:latest
