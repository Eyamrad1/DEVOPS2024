# Utiliser l'image officielle de Java 8 légère basée sur Alpine
FROM openjdk:8-jdk-alpine

# Exposer le port sur lequel l'application écoute (ici, 8082 est le port par défaut pour Spring Boot)
EXPOSE 8082

# Copier le fichier JAR généré dans le conteneur
ADD target/tpFoyer-17-0.0.1.jar 

# Commande d'exécution de l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/tpFoyer-17-1.0.jar"]
