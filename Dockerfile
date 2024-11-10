# Utiliser une image de base Java
FROM openjdk:17-jdk-alpine

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré dans l'image Docker
COPY target/tpFoyer-17-0.0.1.jar /app/tpFoyer-17-0.0.1.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8081

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "tpFoyer-17-0.0.1.jar"]
