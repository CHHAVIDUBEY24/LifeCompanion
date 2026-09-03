# Build Stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy pom.xml and source files
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Setup application directory and permissions for embedded H2 database
RUN mkdir -p /app/data && \
    addgroup -S appgroup && adduser -S appuser -G appgroup && \
    chown -R appuser:appgroup /app

USER appuser

# Copy the fat JAR
COPY --from=build --chown=appuser:appgroup /app/target/life-companion-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080 10000

# Optimized memory flags for Render 512MB free tier
ENV PORT=10000
ENV JAVA_OPTS="-Xmx384m -Xss512k -XX:+UseSerialGC -Djava.security.egd=file:/dev/./urandom"

# Start the Spring Boot application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]
