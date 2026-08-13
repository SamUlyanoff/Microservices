package ru.microservices.user_service.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.microservices.common_events.UserCreatedEvent;
import ru.microservices.user_service.mapper.UserMapper;
import ru.microservices.user_service.repository.UserRepository;
import tools.jackson.databind.ObjectMapper;

@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = "user.created", groupId = "userService")
    @Transactional
    public void createUser(String payload) {
        logger.info("Обработка топика user.created");

        UserCreatedEvent userCreatedEvent = objectMapper.readValue(payload, UserCreatedEvent.class);
        logger.info("Событие, принятое методом: {}", userCreatedEvent);

        var email = userCreatedEvent.email();

        if (checkEmailExistence(email)) {
            //TODO: здесь мы хоть и отменили регистрацию пользователя, но в ответ на запрос auth-service возвращает 201Created. Как правильно это обработать?
            logger.error("Пользователь с email`ом {} уже существует!", email);
            return;
        }

        userRepository.save(userMapper.mapUser(userCreatedEvent));

        logger.info("Пользователь с email {} создан", email);
    }

    /**
     * Если в БД находится email, то возвращает true
     * Если в БД нет такого email, то возвращает false
     */
    @Transactional
    public boolean checkEmailExistence(String email) {
        return userRepository.getUserByEmail(email) != null;
    }
}
