FROM maven:3.5-jdk-8-alpine AS build-env
WORKDIR /app
COPY . .
ARG MAVEN_OPTS
RUN mvn clean package -DskipTests
FROM openjdk:8-jre-alpine
RUN mkdir -p /root/.m2 && chmod -R g+rws,a+rx /root/.m2
WORKDIR /usr/local/app/
COPY --from=build-env /app/target/*.jar /usr/local/app/
RUN mv /usr/local/app/challenge*.jar /usr/local/app/challenge.jar
EXPOSE 8585
CMD ["sh", "-c", "java ${JVM_OPTS} -jar /usr/local/app/challenge.jar"]
