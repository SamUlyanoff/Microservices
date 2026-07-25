package ru.microservices.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.microservices.user_service.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
