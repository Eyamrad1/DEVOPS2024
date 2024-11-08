# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the target folder into the container
COPY target/tpFoyer-17-2.4.jar /app/tpFoyer-17.jar

# Expose the port the app runs on
EXPOSE 8082

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/tpFoyer-17.jar"]
