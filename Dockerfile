# Step 2: Run the application
FROM openjdk:17
WORKDIR /app

# Copy the jar file from the target folder into the container
COPY target/tpFoyer-17-0.0.1.jar /app/tpFoyer-17-0.0.1.jar

# Expose the port the app runs on
EXPOSE 8082

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/tpFoyer-17-0.0.1.jar"]
