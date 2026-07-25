package ru.microservices.auth_service.models;

public record UserRequest(String firstName, String lastName, String email, Character[] password) {
}
