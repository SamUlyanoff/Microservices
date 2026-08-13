package ru.microservices.auth_service.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Передаваемое имя не может быть пустым")
        @Pattern(
                regexp = "^[А-Яа-яA-Za-z]+$",
                message = "Введённое имя не соответствует требованиям"
        )
        @Size(min = 1, max = 50, message = "Размер вводимого имени не соответствует границам [0; 50] символов")
        String firstName,

        @NotBlank(message = "Передаваемая фамилия не может быть пустой")
        @Pattern(
                regexp = "^[А-Яа-яA-Za-z]+$",
                message = "Введённая фамилия не соответствует требованиям"
        )
        @Size(min = 1, max = 50, message = "Размер вводимой фамилии не соответствует границам [0; 50] символов")
        String lastName,

        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Введённый email не соответствует требованиям")
        @Size(max = 75, message = "Размер email не должен превышать 75 символов")
        String email,

        char[] password) {

}
