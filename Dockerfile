FROM openjdk:17-jdk-alpine

COPY target/meteo-0.0.1-SNAPSHOT.jar meteo-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/meteo-0.0.1-SNAPSHOT.jar"]