# Product + Order Microservices

Spring Boot 4.1.1 + Spring Cloud 2025.1.2 + Java 17.

This single Maven repository contains four runnable applications:

1. Eureka Server - 8761
2. API Gateway - 8080
3. Product Service - 8081
4. Order Service - 8082

Order Service demonstrates all four HTTP client styles:

- RestTemplate
- RestClient
- WebClient
- OpenFeign

Eureka provides service discovery and Spring Cloud LoadBalancer resolves
`http://product-service/...` to the registered Product Service instance.

## Start order

Run these applications in this order:

1. EurekaServerApplication
2. ProductServiceApplication
3. OrderServiceApplication
4. ApiGatewayApplication

Then open:

http://localhost:8761

## Direct Product Service

GET:
http://localhost:8081/api/products

GET:
http://localhost:8081/api/products/1

## Through API Gateway

GET:
http://localhost:8080/api/products

GET:
http://localhost:8080/api/products/1

## Test each HTTP client

RestTemplate:
POST http://localhost:8080/api/orders/resttemplate?orderId=100&productId=1&quantity=2

RestClient:
POST http://localhost:8080/api/orders/restclient?orderId=101&productId=1&quantity=2

WebClient:
POST http://localhost:8080/api/orders/webclient?orderId=102&productId=1&quantity=2

Feign:
POST http://localhost:8080/api/orders/feign?orderId=103&productId=1&quantity=2

The response contains `clientUsed` so you can see which client performed
the Product Service call.

## Architecture

Client
  |
  v
API Gateway :8080
  |
  +---- lb://PRODUCT-SERVICE ----> Product Service :8081
  |
  +---- lb://ORDER-SERVICE ------> Order Service :8082
                                      |
                                      +-- RestTemplate --+
                                      +-- RestClient ----+--> Eureka
                                      +-- WebClient -----+--> Product Service
                                      +-- Feign ----------+

Eureka Server :8761 keeps the service registry.

## Maven

From the root:

mvn clean install

If using the Maven wrapper:

Windows:
mvnw.cmd clean install

Linux/macOS:
./mvnw clean install

Java:
17
