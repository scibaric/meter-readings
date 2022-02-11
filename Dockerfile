FROM openjdk:latest
RUN cd /home/ && ls
COPY target/*.jar meterreadings.jar
ENTRYPOINT ["java","-jar","/meterreadings.jar"]