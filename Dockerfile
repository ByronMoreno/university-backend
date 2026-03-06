## Imagen multi-stage para backend Spring Boot con Gradle y Java 21

# Etapa de build: usar Gradle con JDK 21 para compilar el proyecto y generar el jar
#FROM gradle:8.7-jdk21-alpine AS build
# ---------- BUILD STAGE ----------
FROM gradle:9.0-jdk21-alpine AS build

WORKDIR /app

# Copiamos los archivos de build primero para aprovechar caché
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Copiamos el código fuente
COPY src ./src

# Construimos el jar ejecutable de Spring Boot
RUN gradle clean bootJar --no-daemon

# ---------- RUNTIME STAGE ----------
# Etapa de runtime: solo el JRE liviano para ejecutar el jar
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiamos el jar generado desde la etapa de build
# El nombre real del jar es algo como login-0.0.1-SNAPSHOT.jar
COPY --from=build /app/build/libs/*.jar app.jar

# Perfil de producción
ENV SPRING_PROFILES_ACTIVE=prod

# Puerto interno de la aplicación
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

