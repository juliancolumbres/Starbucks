all: clean

clean:
	mvn clean

compile:
	mvn compile

build: compile
	mvn package

run: build
	java -jar target/starbucks-worker-1.0.jar --MYSQL_USERNAME=root --MYSQL_PASSWORD=cmpe172

docker-build: build
	docker build -t starbucks-worker .
	docker images