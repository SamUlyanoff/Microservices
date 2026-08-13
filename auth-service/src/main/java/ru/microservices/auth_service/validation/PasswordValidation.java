package ru.microservices.auth_service.validation;

import org.springframework.stereotype.Component;
import ru.microservices.auth_service.exceptions.PasswordValidationException;

@Component
public class PasswordValidation {

    public void validate(char[] password){
        if(password == null) throw new PasswordValidationException("Пароль не может быть пустым");
        if(password.length < 8) throw new PasswordValidationException("Длина пароля должна быть не мене 8 символов");

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for(Character c: password){
            if(Character.isDigit(c)) {hasDigit = true; continue;}
            if(Character.isUpperCase(c)) {hasUpper = true; continue;}
            if(Character.isLowerCase(c)) hasLower = true;
        }

        if(!hasUpper||!hasLower||!hasDigit){
            throw new PasswordValidationException("Пароль должен содержать минимум одну заглавную букву, одну строчную обувку и одну цифру");
        }
    }
}
