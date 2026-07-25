package ru.microservices.user_service.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.microservices.user_service.avro.UserCreatedEvent;
import ru.microservices.user_service.entity.User;
import ru.microservices.user_service.mapper.UserMapper;
import ru.microservices.user_service.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @KafkaListener(topics = "user.created")
    @Transactional
    public User createUser(UserCreatedEvent userCreatedEvent){
        return userRepository.save(userMapper.mapUser(userCreatedEvent));
    }
}
