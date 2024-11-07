FROM openjdk:17-jdk-slim
EXPOSE 8082
COPY ./target/tpFoyer-17-2.4.jar tpFoyer-17-2.4.jar
ENTRYPOINT ["java", "-jar", "/tpFoyer-17-2.4.jar"]


