package ru.microservices.user_service.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.microservices.user_service.avro.UserCreatedEvent;
import ru.microservices.user_service.mapper.UserPasswordMapper;
import ru.microservices.user_service.repository.UserPasswordRepository;

@Service
@AllArgsConstructor
public class UserPasswordService {

    private final UserPasswordRepository repository;
    private final UserPasswordMapper mapper;

    @KafkaListener(topics = "user.created")
    @Transactional
    public void saveUserPassword(UserCreatedEvent userCreatedEvent){
        try {
            repository.save(mapper.mapUserPassword(userCreatedEvent));
        }catch (Exception e){
            throw new RuntimeException("Ошибка сохранения пароля в БД:" + e.getMessage());
        }
    }
}
