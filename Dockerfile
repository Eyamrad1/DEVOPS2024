# Utiliser une image de base Java
FROM openjdk:17-jdk-alpine

# Variables pour Nexus
ARG NEXUS_USERNAME=admin
ARG NEXUS_PASSWORD=AsmaAsma1@123
ARG NEXUS_URL=http://192.168.1.18:8081/repository/maven-releases/tn/esprit/tpFoyer-17/0.0.1/tpFoyer-17-0.0.1.jar

# Définir le répertoire de travail
WORKDIR /app

# Installer wget et télécharger le fichier JAR depuis Nexus
RUN apk add --no-cache wget && \
    wget --user=$NEXUS_USERNAME --password=$NEXUS_PASSWORD -O tpFoyer-17-0.0.1.jar "$NEXUS_URL"

# Exposer le port sur lequel l'application écoute
EXPOSE 8082

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "tpFoyer-17-0.0.1.jar"]