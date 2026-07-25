package ru.microservices.user_service.mapper;

import org.springframework.stereotype.Component;
import ru.microservices.user_service.avro.UserCreatedEvent;
import ru.microservices.user_service.entity.User;

import java.util.UUID;

@Component
public class UserMapper {

    public User mapUser(UserCreatedEvent userCreatedEvent){
        return User.builder()
                .id(UUID.fromString(userCreatedEvent.getUserId()))
                .firstName(userCreatedEvent.getFirstName())
                .lastName(userCreatedEvent.getLastName())
                .email(userCreatedEvent.getEmail())
                .createdAt(userCreatedEvent.getCreatedAt())
                .build();
    }
}
