# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine



# Copy the jar file from the target folder into the container
COPY target/tpFoyer-17-0.0.1.jar /app/tpFoyer-17.jar

# Expose the port the app runs on
EXPOSE 8082

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/tpFoyer-17.jar"]