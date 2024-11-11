# Utilise l'image Java 8 légère basée sur Alpine
FROM openjdk:8-jdk-alpine

# Expose le port sur lequel l'application écoute
EXPOSE 8082

# Copier le fichier JAR généré dans le conteneur
ADD target/tpFoyer-17-1.0.jar tpFoyer-17-1.0.jar

# Commande d'exécution de l'application
ENTRYPOINT ["java", "-jar", "/tpFoyer-17-1.0.jar"]
