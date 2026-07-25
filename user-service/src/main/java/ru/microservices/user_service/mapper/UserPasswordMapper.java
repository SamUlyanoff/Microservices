package ru.microservices.user_service.mapper;

import org.springframework.stereotype.Component;
import ru.microservices.user_service.avro.UserCreatedEvent;
import ru.microservices.user_service.entity.UserPassword;

import java.util.UUID;

@Component
public class UserPasswordMapper {

    public UserPassword mapUserPassword(UserCreatedEvent userCreatedEvent){
        return UserPassword.builder()
                .userId(UUID.fromString(userCreatedEvent.getUserId()))
                .hashedPassword(userCreatedEvent.getPassword())
                .build();
    }
}
