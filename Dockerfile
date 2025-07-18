# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set working directory in container
WORKDIR /app

# Copy the jar file built by Maven
COPY target/learning-project-0.0.1-SNAPSHOT.jar app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
