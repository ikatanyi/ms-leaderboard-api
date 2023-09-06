# AS <NAME> to name this stage as maven
# Java 11
FROM openjdk:11
LABEL maintainer="kennedy.ikatanyi"
ADD target/ms-leaderboard-api-1.0.jar ms-leaderboard-api-docker.jar
ENTRYPOINT ["java","-jar","ms-leaderboard-api-docker.jar"]






