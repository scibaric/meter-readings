FROM openjdk:latest
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} meterreadings.jar
ENTRYPOINT ["java","-jar","/meterreadings.jar"]