package ru.microservices.auth_service.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.microservices.auth_service.avro.UserCreatedEvent;
import ru.microservices.auth_service.converter.ArrayToStringConverter;
import ru.microservices.auth_service.models.UserRequest;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;
    private final ArrayToStringConverter arrayToStringConverter;

    public String createUser(UserRequest userRequest){
        String userId = UUID.randomUUID().toString();
        UserCreatedEvent userCreatedEvent = UserCreatedEvent.newBuilder()
                .setUserId(userId)
                .setFirstName(userRequest.firstName())
                .setLastName(userRequest.lastName())
                .setEmail(userRequest.email())
                .setPassword(passwordEncoder.encode(arrayToStringConverter.arrayToString(userRequest.password())))
                .setCreatedAt(Instant.now())
                .build();

        kafkaTemplate.send("user.created", userCreatedEvent);

        return userId;
    }
}
