package ru.microservices.auth_service.gRPC;

public interface UserServiceClient {

    /**
     * Проверка, находится ли передаваемый email в БД
     * При наличии возвращает true
     * При отсутствии возвращает false
     */
    Boolean checkEmailExistence(String email);
}
