# Build stage
FROM gradle:8.4-jdk21 AS build

# Install CA certificates
RUN apt-get update && \
    apt-get install -y ca-certificates && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy build files first to leverage cache
COPY build.gradle.kts settings.gradle.kts /app/
COPY gradle /app/gradle
RUN gradle dependencies

# Copy source code and build
COPY src /app/src
RUN gradle build --no-daemon -x test

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

EXPOSE 8080

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]