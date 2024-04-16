FROM openjdk:21
EXPOSE 8080
ADD target/currency-exchange-service.jar currency-exchange-service.jar
ENTRYPOINT ["java","-jar","/currency-exchange-service.jar"]