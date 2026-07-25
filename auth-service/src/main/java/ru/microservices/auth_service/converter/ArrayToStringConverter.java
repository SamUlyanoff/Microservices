package ru.microservices.auth_service.converter;

import org.springframework.stereotype.Component;

@Component
public class ArrayToStringConverter {

    public String arrayToString(Character [] array){
        if(array == null){
            throw new NullPointerException("Массив не может быть пустым");
        }
        StringBuilder result = new StringBuilder();
        for(Character character: array){
            result.append(character);
        }
        return result.toString();
    }
}
