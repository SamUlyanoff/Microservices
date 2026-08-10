package ru.microservices.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.microservices.auth_service.entity.AuthOutBoxEvent;

import java.util.List;
import java.util.UUID;

public interface AuthOutBoxRepository extends JpaRepository<AuthOutBoxEvent, UUID> {

    @Query(value = """
            SELECT
                aoe
            FROM outbox_auth aoe
            WHERE aoe.sent = false
            ORDER BY aoe.created_at ASC
            LIMIT 10
            FOR UPDATE SKIP LOCKED
            """, nativeQuery = true)
    List<AuthOutBoxEvent> getEventsWithoutSent();

    @Modifying
    @Query("""
            UPDATE
                AuthOutBoxEvent aoe
            SET aoe.sent =:sent
            WHERE aoe.id =:id
            """)
    void updateSentStatusById(@Param("id") UUID id, @Param("sent") boolean sent);
}
