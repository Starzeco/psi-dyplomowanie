FROM maven:3.8.1-jdk-11 AS build
WORKDIR /srv
COPY . /srv
RUN mvn clean -Dmaven.test.skip=true package

FROM openjdk:11-jre-slim
RUN apt-get update
COPY --from=build /srv/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
