FROM openjdk:11
EXPOSE 8080
ADD ./target/spring-starbucks-api-3.1.jar /srv/spring-starbucks-api-3.1.jar
CMD java -jar /srv/spring-starbucks-api-3.1.jar --spring.profiles.active=test
# CMD java -jar /srv/spring-starbucks-api-3.1.jar 