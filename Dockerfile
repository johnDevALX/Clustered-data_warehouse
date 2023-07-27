FROM openjdk:17-alpine

COPY target/Clustered-data_warehouse-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]