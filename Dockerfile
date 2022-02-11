FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} meterreadings.jar
ENTRYPOINT ["java","-jar","/meterreadings.jar"]