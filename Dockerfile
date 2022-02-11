FROM openjdk:latest
COPY target/*.jar meterreadings.jar
ENTRYPOINT ["java","-jar","/meterreadings.jar"]