# Use official OpenJDK image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy your app files into the container
COPY . .

# Build the Spring Boot app
RUN ./mvnw clean package -DskipTests

# Expose port 8080 (Spring Boot default)
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "target/todo_mongodb_app-0.0.1-SNAPSHOT.jar"]
