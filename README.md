
# Smoke assignment - Middleware

### MVP

The aim is to create a service that provides temperature information based on location.
The source data should come from [Open_meteo](https://open-meteo.com/).

### Tech Stack

* SpringBoot framework
* MongoDb
* Kafka
* Java 17

## Architecture

The project uses hexagonal architecture or ports and adapters pattern to keep a clean architecture.

### Infrastructure layer
This layer includes all the adapters and side components required for inbound and outbound communication with other systems.

#### Bean configuration
It includes Spring configuration, to provide the required dependencies to the application layer through constructor dependency injection.

#### Inbound
Inbound package contains
* REST Controller, as well as the required model (DTOs) and mappers.

#### Outbound
Outbound package contains:
* REST Client to perform request to Open-Meteo API, as well as the required model and mappers.
* Mongo persistence adapter repository to interact with mongodb.
* Cache, managed through aspects and custom annotations
  * ForecastCacheable: Gets the temperature data, if present, from the database. It stores the new data obtained from Open_meteo in the database and returns it.
      A creation date is used in db documents to identify when it was created and evaluate if it is expired based on a configured parameter. 
  * ForecastCacheEvict: Deletes the temperature data from the database, even if it expired.
* Kafka event producer to send messages to a specific topic. It has been implemented as an aspect so no modifications need to be done to the corresponding method. It is executed when the temperature is returned by the application layer.
  The messages are sent asynchronously. Whether it succeeds or not, it is not bocking the rest of the application flow. For the moment it just logs the outcome of the message delivery.

### Application layer

Tha application layer includes input and output ports or interfaces, for the business logic to be accessed from the infrastructure layer.
It also includes the input port adapters or implementations under the service package.
Dependencies are injected through constructors and no framework annotations are used to keep it independent fom infrastructure changes.

### Domain layer
The domain layer includes the domain model. It also includes a configuration Record for any required external parameters. 
At the moment they are injected from a properties file by the infrastructure layer, but they could come from the cloud centralized configuration, as well as other infrastructure parameters.


## Testing
JUnit and Mockito were used in some unit and integration tests, to evaluate whether the code does what it is
supposed to. 
A specific application-test.properties is used for tests.

### Test Coverage
JaCoCo plugin was added to generate the test coverage report.

## Dockerfile
A Dockerfile has been included. It requires a mongodb running with the database and credentials specified in application.properties.

## Logging
Logging aspect was created to trace the controller requests and responses.
Logs are sent out to console and /var/log directory. It can be customized in application.properties 

## Improvement points
* Extend the test suite with more edge and exception handling cases.
* Prepare some load tests.
* Add logback for additional logging configuration.
* Add custom exception handling. 
* Add Spring Security to the API with OAuth2.
* Consider a version of the application using reactive programming with Spring WebFlux, for asynchronous non-blocking
  data processing.