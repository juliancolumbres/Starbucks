all: clean

clean:
	mvn clean

compile:
	mvn compile

build: compile
	mvn package

run:
	mvn spring-boot:run

docker-build: build
	docker build -t starbucks-cashier-app .
	docker images

compose-up:
	docker-compose up -d

lb-up:
	docker-compose up -d lb

gumball-up:
	docker-compose up -d gumball

worker-up:
	docker-compose up -d worker

rabbit-up:
	docker-compose up -d rabbit

mysql-up:
	docker-compose up -d mysql

compose-down:
	docker-compose down