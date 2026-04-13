FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# El build.yml ya construyó el JAR con el frontend incluido, solo lo copiamos
COPY target/architectureBank-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]