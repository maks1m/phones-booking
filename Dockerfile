FROM openjdk:11-jdk-slim
WORKDIR /app
CMD ["gradlew", "clean", "jar"]
COPY build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8080
