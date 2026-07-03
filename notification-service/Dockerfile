# Сборка
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Копируем root файлы для определения общих настроек
COPY build.gradle settings.gradle gradlew gradlew.bat ./
COPY gradle ./gradle

COPY notification-service/build.gradle ./notification-service/
COPY notification-service/src ./notification-service/src
RUN ./gradlew :notification-service:clean :notification-service:build -x test --no-daemon

# Запуск
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/notification-service/build/libs/*.jar app.jar
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "app.jar"]