# Utilise une image OpenJDK 17 légère pour le runtime
FROM openjdk:17-jdk-alpine

# Spécifie le port sur lequel l'application va écouter
EXPOSE 8082

# Copie le fichier .jar généré dans l'image Docker
ADD target/tpFoyer-17-1.0.0.jar tpFoyer-17-1.0.0.jar

# Définit le point d'entrée pour lancer l'application
ENTRYPOINT ["java", "-jar", "/tpFoyer-17-1.0.0.jar"]
