FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app
WORKDIR /app
COPY ./target/auth-0.0.1-SNAPSHOT.jar ./service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./service.jar"]