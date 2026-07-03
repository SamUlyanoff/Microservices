# Сборка
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Копируем root файлы для определения общих настроек
COPY build.gradle settings.gradle gradlew gradlew.bat ./
COPY gradle ./gradle

COPY order-service/build.gradle ./order-service/
COPY order-service/src ./order-service/src
RUN ./gradlew :order-service:clean :order-service:build -x test --no-daemon

# Запуск
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/order-service/build/libs/*.jar app.jar
EXPOSE 8094
ENTRYPOINT ["java", "-jar", "app.jar"]