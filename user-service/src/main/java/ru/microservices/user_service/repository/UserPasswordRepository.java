package ru.microservices.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.microservices.user_service.entity.UserPassword;

import java.util.UUID;

public interface UserPasswordRepository extends JpaRepository<UserPassword, UUID> {

}
