package ru.microservices.auth_service.repozitory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.microservices.auth_service.entity.UserPassword;

import java.util.UUID;

public interface UserPasswordRepository extends JpaRepository<UserPassword, UUID> {

}
