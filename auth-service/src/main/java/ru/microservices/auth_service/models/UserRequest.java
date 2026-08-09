package ru.microservices.auth_service.models;

import java.util.Arrays;

public record UserRequest(String firstName, String lastName, String email, Character[] password) {

    @Override
    public String toString() {
        return "UserRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password=" + Arrays.toString(password) +
                '}';
    }
}
