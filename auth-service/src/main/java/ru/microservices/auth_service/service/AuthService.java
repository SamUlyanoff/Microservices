package ru.microservices.auth_service.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.microservices.auth_service.converter.ArrayToStringConverter;
import ru.microservices.auth_service.entity.AuthOutBoxEvent;
import ru.microservices.auth_service.models.UserRequest;
import ru.microservices.auth_service.repository.AuthOutBoxRepository;
import ru.microservices.auth_service.validation.PasswordValidation;
import ru.microservices.common_events.UserCreatedEvent;
import ru.microservices.auth_service.entity.UserPassword;
import ru.microservices.auth_service.repository.UserPasswordRepository;
import ru.microservices.common_events.outbox.SentStatus;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final ArrayToStringConverter arrayToStringConverter;
    private final UserPasswordRepository userPasswordRepository;
    private final AuthOutBoxRepository authOutBoxRepository;
    private final ObjectMapper objectMapper;
    private final PasswordValidation passwordValidation;

    @Transactional
    public String createUser(UserRequest userRequest){

        String email = userRequest.email();

        String userId = UUID.randomUUID().toString();

        try {
            passwordValidation.validate(userRequest.password());
            userPasswordRepository.save(UserPassword.builder()
                    .userId(UUID.fromString(userId))
                    .hashedPassword(passwordEncoder.encode(arrayToStringConverter.arrayToString(userRequest.password())))
                    .build());
        }finally {
            Arrays.fill(userRequest.password(), '\0');
        }

        //создание события кафка
        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                .id(userId)
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(email)
                .createdAt(Instant.now())
                .build();

        //Создание записи в таблице outbox_auth
        authOutBoxRepository.save(
                AuthOutBoxEvent.builder()
                .aggregateId(userId)
                .eventType("user.created")
                .payload(objectMapper.writeValueAsString(userCreatedEvent))
                .createdAt(Instant.now())
                .sent(false)
                .tryCount(0)
                .status(SentStatus.PENDING)
                .build()
        );

        return userId;
    }
}
