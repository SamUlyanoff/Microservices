package ru.microservices.common_events.outbox;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractOutBoxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**ИДЕНТИФИКАТОР ДЛЯ АГРЕГАЦИИ<br></br>
     * События с одним aggregateId будут попадать в одну партицию и обрабатываться поочередно
     */
    @Column(name = "aggregate_id", nullable = false)
    private String aggregateId;

    /**Наименование топика*/
    @Column(name = "event_type", nullable = false)
    private String eventType;

    /**Событие KAFKA*/
    @Column(name = "payload", nullable = false)
    private String payload;

    /**Статус отправки события*/
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SentStatus status;

    @Column(name = "count_of_try", nullable = false)
    private Integer tryCount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "sent", nullable = false)
    @Builder.Default
    private boolean sent = false;

}
