FROM openjdk:17-jdk-alpine

COPY target/meteo.jar meteo.jar

ENTRYPOINT ["java","-jar","/meteo.jar"]