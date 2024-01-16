FROM openjdk:18-ea-11-jdk-alpine
COPY target/*.jar booking-producer-service.jar
ENTRYPOINT ["java","-jar","/booking-producer-service.jar"]