FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=build/libs/event-service.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

