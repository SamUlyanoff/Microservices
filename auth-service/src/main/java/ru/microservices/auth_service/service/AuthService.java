package ru.microservices.auth_service.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.microservices.auth_service.converter.ArrayToStringConverter;
import ru.microservices.auth_service.models.UserRequest;
import ru.microservices.common_events.UserCreatedEvent;
import ru.microservices.auth_service.entity.UserPassword;
import ru.microservices.auth_service.repozitory.UserPasswordRepository;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;
    private final ArrayToStringConverter arrayToStringConverter;
    private final UserPasswordRepository userPasswordRepository;

    @Transactional
    public String createUser(UserRequest userRequest){
        String userId = UUID.randomUUID().toString();
        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                .id(userId)
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .createdAt(Instant.now())
                .build();

         kafkaTemplate.send("user.created", userCreatedEvent);

         //TODO: как правильно сделать?
         userPasswordRepository.save(UserPassword.builder()
                 .userId(UUID.fromString(userId))
                 .hashedPassword(passwordEncoder.encode(arrayToStringConverter.arrayToString(userRequest.password())))
                 .build());

        return userId;
    }
}
