FROM openjdk:latest
RUN cd /home/
RUN ls
COPY target/*.jar meterreadings.jar
ENTRYPOINT ["java","-jar","/meterreadings.jar"]