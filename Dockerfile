# AS <NAME> to name this stage as maven
FROM maven:3.8.4 AS maven

ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/leaderboard
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=admin@_123
ENV SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver
ENV REDIS_HOST=localhost
ENV REDIS_PORT=6379
ENV DEFAULT_TTL=180



WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package -Dmaven.test.skip

# Java 17
FROM openjdk:11-jdk

ARG JAR_FILE=ms-leaderboard-api-1.0.jar

WORKDIR /opt/app


COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/
EXPOSE 8080
ENTRYPOINT ["java","-jar","ms-leaderboard-api-1.0.jar"]






