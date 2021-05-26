# Dockerfile
FROM maven:3.6.3-jdk-11 as maven

MAINTAINER  Lololo <something@email.gg>

ENV PROJECT_DIR=/opt/backend
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY ./pom.xml $PROJECT_DIR/pom.xml
RUN mvn dependency:resolve clean
COPY ./src $PROJECT_DIR/src
RUN mvn package

FROM openjdk:11

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=maven opt/backend/target/JavaApp*.jar $PROJECT_DIR/app.jar
EXPOSE 8082
CMD ["java", "-jar", "/opt/project/app.jar"]
