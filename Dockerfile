# Étape 1 : Construire l'application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY tpFoyer-17/pom.xml .
COPY tpFoyer-17/src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Exécuter l'application
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
