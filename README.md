# Meter readings

Meter readings is a REST Service for fetching and storing energy consumption for specific meter by
year and month. For simple use and testing purposes we are using H2
in-memory database. Service is started on port 8080.

## Test, build and run

Maven automation tool is used for testing the service and building JAR.
Testing and building is started with next command
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

