# ---------- BUILD ----------
FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn -B -q dependency:go-offline

COPY src ./src

RUN mvn -B -q clean install -DskipTests

# ---------- RUNTIME ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

RUN useradd -m appuser
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]