package ru.microservices.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.microservices.auth_service.entity.AuthOutBoxEvent;
import ru.microservices.common_events.outbox.SentStatus;

import java.util.List;
import java.util.UUID;

public interface AuthOutBoxRepository extends JpaRepository<AuthOutBoxEvent, UUID> {

    @Query(value = """
            SELECT
                *
            FROM outbox_auth aoe
            WHERE aoe.sent = false AND aoe.status!='FAILED'
            ORDER BY aoe.created_at ASC
            LIMIT 10
            FOR UPDATE SKIP LOCKED
            """, nativeQuery = true)
    List<AuthOutBoxEvent> getEventsWithoutSent();

    @Modifying
    @Query("""
            UPDATE
                AuthOutBoxEvent aoe
            SET aoe.sent =:sent, aoe.status=:status, aoe.tryCount =:tryCount
            WHERE aoe.id =:id
            """)
    void updateSentStatus(
            @Param("id") UUID id,
            @Param("sent") boolean sent,
            @Param("status")SentStatus status,
            @Param("tryCount") Integer tryCount);

}
