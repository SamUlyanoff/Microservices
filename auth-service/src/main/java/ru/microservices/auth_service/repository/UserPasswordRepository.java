package ru.microservices.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.microservices.auth_service.entity.UserPassword;

import java.util.UUID;

public interface UserPasswordRepository extends JpaRepository<UserPassword, UUID> {

    @Modifying
    @Query("""
            DELETE
            FROM UserPassword up
            WHERE up.userId =:userId
            """)
    void deleteByUserId(@Param("userId") UUID userId);
}
