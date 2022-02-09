# Meter readings

Meter readings is a REST Service for fetching and storing energy consumption for specific meter by
year and month. For simple use and testing purposes we are using H2
in-memory database. Service is started on port 8080.

## Test, build and run

Maven automation tool is used for testing the service and building JAR.
Testing and building is started with next command inside the project
`./mvnw clean test package`. 

All test should pass and JAR is generated in
`./target` directory which should appear inside project upon completion of
the last command. JAR can be started with command 
`java -jar ./target/meter-readings-0.0.1-SNAPSHOT.jar`. You should be able
to access the REST service endpoints through browser or Postman. Service
starts on localhost and port 8080, testing URLs are:
- http://localhost:8080/api/meter/1/consumption/aggregation/year/2020
  * Produces JSON
    ```
    {
      "year": 2020,
      "total": 195
    }
    ```
- http://localhost:8080/api/meter/1/year/2020 
  * Produces JSON
    ```
    {
      "year": 2020,
      "monthlyEnergyConsumption": {
        "June": 18,
        "October": 25,
        "December": 22,
        "May": 16,
        "September": 8,
        "March": 9,
        "July": 12,
        "January": 11,
        "February": 14,
        "April": 23,
        "August": 17,
        "November": 20
      }
    }
    ```
- http://localhost:8080/api/meter/1/year/2020/month/1
  * Produces JSON
    ```
    {
      "year": 2020,
      "monthlyEnergyConsumption": {
        "January": 18
      }
    }
    ```

Endpoint for saving meter reading is `/api/meter/reading` and it expects
JSON object, the same object is returned.
```
{
    "year": 2021,
    "month": 1,
    "energyConsumed": 15,
    "meterId": 1
}
```

## Docker

Application can be started as Docker container. Dockerfile holds specification for creating the
image. To be able to create docker image, previously command `./mvnw clean test package` has to be run
inside the project.

Running commands creates image and starts the container at port 8080: 
- `docker build -t meter-readings -f Dockerfile .` - creates docker image with tag meter-readings
- `docker run --name meter-readings -p 8080:8080 meter-readings` - runs docker container with name 
meter-readings on port 8080.

## OpenAPI

OpenAPI with swagger UI is available after starting the service.\
Links to docs are http://localhost:8080/swagger-ui/index.html or http://localhost:8080/swagger-ui-meterreadings.html. 

## TODO

- [x] Parametrize integration tests
- [ ] Parametrize unit tests
- [ ] Create proper mapper for before/after save/update meter reading
