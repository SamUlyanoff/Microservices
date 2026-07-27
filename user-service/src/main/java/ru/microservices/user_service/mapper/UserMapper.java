package ru.microservices.user_service.mapper;

import org.springframework.stereotype.Component;
import ru.microservices.common_events.UserCreatedEvent;
import ru.microservices.user_service.entity.User;

import java.util.UUID;

@Component
public class UserMapper {

    public User mapUser(UserCreatedEvent userCreatedEvent){
        return User.builder()
                .id(UUID.fromString(userCreatedEvent.id()))
                .firstName(userCreatedEvent.firstName())
                .lastName(userCreatedEvent.lastName())
                .email(userCreatedEvent.email())
                .createdAt(userCreatedEvent.createdAt())
                .build();
    }
}
