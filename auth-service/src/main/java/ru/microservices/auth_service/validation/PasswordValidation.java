package ru.microservices.auth_service.validation;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PasswordValidation {

    public void validate(Character[] password){
        if(password == null) throw new RuntimeException("Пароль не может быть пустым");
        if(password.length <= 8) throw new RuntimeException("Длина пароля должна быть не мене 8 символов");

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for(Character c: password){
            if(Character.isUpperCase(c)) hasUpper = true;
            if(Character.isLowerCase(c)) hasLower = true;
            if(Character.isDigit(c)) hasDigit = true;
        }

        Arrays.fill(password, '\0');

        if(!hasUpper||!hasLower||!hasDigit){
            throw new RuntimeException("Пароль должен содержать минимум одну заглавную букву, одну строчную обувку и одну цифру");
        };
    }
}
