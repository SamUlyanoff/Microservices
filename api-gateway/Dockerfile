# Сборка
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Копируем root файлы для определения общих настроек
COPY build.gradle settings.gradle gradlew gradlew.bat ./
COPY gradle ./gradle

# Копируем конкретный сервис
COPY api-gateway/build.gradle ./api-gateway/
COPY api-gateway/src ./api-gateway/src

# Собираем конкретный сервис
RUN ./gradlew :api-gateway:clean :api-gateway:build -x test --no-daemon

# Запуск
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/api-gateway/build/libs/*.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]