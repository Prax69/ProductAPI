FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app

COPY gradlew ./gradlew
COPY gradle ./gradle
COPY build.gradle* settings.gradle* ./

# Ensure the wrapper script is executable
RUN chmod +x gradlew

# Download dependencies (layer caching optimization)
RUN ./gradlew dependencies --no-daemon || true

# Copy the full source code and build the JAR
COPY . .
RUN ./gradlew bootJar --no-daemon

# Stage 2: Run the app with a lightweight JDK
FROM eclipse-temurin:24-jdk-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
