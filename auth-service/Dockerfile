# Сборка
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Копируем root файлы для определения общих настроек
COPY build.gradle settings.gradle gradlew gradlew.bat ./
COPY gradle ./gradle

COPY auth-service/build.gradle ./auth-service/
COPY auth-service/src ./auth-service/src
RUN ./gradlew :auth-service:clean :auth-service:build -x test --no-daemon

# Запуск
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/auth-service/build/libs/*.jar app.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "app.jar"]