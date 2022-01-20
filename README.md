# User service code challenge 
This is a sample code for Persues company.

## Getting Started
### Built With
* [Spring Boot](https://spring.io/projects/spring-boot) - The Spring boot framework i am using to develop my services.
* [JUnit 5](https://junit.org/junit5/) - The testing framework on JVM.
* [Maven](https://maven.apache.org) - As our build tool.
* Java 8

### Running
For this code challenge I provided a docker file. For running a container, execute these commands respectively:

```bash 
>>  docker build --tag=challenge:latest .
>>  docker run -p8887:8585 challenge:latest
```
The first command builds an image from Dockerfile,
 which is located in the project root,
 and the second one creates a container and runs it.

### Using the APIs
To access the home page, click on the URL in your browser:
[URL](http://localhost:8887/swagger-ui.html#/)

