#------Build--------
#doceker file uses gradle 8

FROM gradle:8-jdk-21-and-22-alpine AS builder


#copy all files to /home/gradle/src
#COPY --chown=gradle:gradle product-service /home/gradle/src
COPY --chown=gradle:gradle . /home/gradle/src

#change work dir
WORKDIR /home/gradle/src

RUN gradle build -x test

#Package Stage
#using openjdk to create final container
FROM openjdk:21-jdk

#create /app dir inside container
RUN mkdir /app
#copy already created jar file inside of homegradle/src/libs into /app folder name it ps.jar
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/event-service.jar

#

ENV MONGO_DB_INIT_USERNAME=admin \
    MONGO_DB_INIT_PASSWORD=password
#expose point 8084 of our container
EXPOSE 8086

