FROM openjdk:17-jdk-alpine
ARG JAR-FILE=target/*.jar
COPY ./HngTaskFive/target/*.jar app.jar
#LABEL authors="Chidinma.Afogu"

ENTRYPOINT ["java", "-jar","/app.jar"]