FROM openjdk:latest
RUN cd /home/
RUN ls
COPY /home/runner/work/meter-readings/meter-readings/target/*.jar meterreadings.jar
ENTRYPOINT ["java","-jar","/meterreadings.jar"]