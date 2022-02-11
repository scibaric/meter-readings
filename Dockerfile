FROM openjdk:latest
COPY ./*.jar meterreadings.jar
ENTRYPOINT ["java","-jar","/meterreadings.jar"]