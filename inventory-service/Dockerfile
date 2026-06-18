# Сборка
FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY build.gradle ./
COPY src ./src
RUN gradle clean build -x test --no-daemon

# Запуск
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "app.jar"]