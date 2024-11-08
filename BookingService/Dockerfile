FROM ubuntu:latest
LABEL authors="omele"

EXPOSE 8081

RUN chmod +x booking-service
ENTRYPOINT ["java", "-jar", "/app/booking-service.jar"]