FROM openjdk:latest
RUN pwd && ls
COPY ./*.jar meterreadings.jar
ENTRYPOINT ["java","-jar","/meterreadings.jar"]