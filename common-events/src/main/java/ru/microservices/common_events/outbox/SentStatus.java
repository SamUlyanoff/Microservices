package ru.microservices.common_events.outbox;

import lombok.Getter;

@Getter
public enum SentStatus {
    PENDING("Ожидает отправки"),
    SENT("Успешно отправлено"),
    FAILED("Ошибка при отправке");

    private final String description;

    SentStatus(String description) {
        this.description = description;
    }

}
