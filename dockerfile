FROM openjdk:21-slim
WORKDIR /app
COPY target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "auth-service.jar"]
EXPOSE 8082