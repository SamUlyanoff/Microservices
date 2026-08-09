package ru.microservices.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.microservices.user_service.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * По получаемому email возвращает соответствующего пользователя
     */
    @Query("""
            SELECT
                u
            FROM User u
            WHERE u.email =:email
            """)
    User getUserByEmail(@Param("email") String email);
}
