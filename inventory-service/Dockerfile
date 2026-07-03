# Сборка
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Копируем root файлы для определения общих настроек
COPY build.gradle settings.gradle gradlew gradlew.bat ./
COPY gradle ./gradle

COPY inventory-service/build.gradle ./inventory-service/
COPY inventory-service/src ./inventory-service/src
RUN ./gradlew :inventory-service:clean :inventory-service:build -x test --no-daemon

# Запуск
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/inventory-service/build/libs/*.jar app.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "app.jar"]