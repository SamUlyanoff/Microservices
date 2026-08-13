package ru.microservices.common_events;

import lombok.Builder;

import java.time.Instant;

@Builder
public record UserCreatedEvent(String id, String firstName, String lastName, String email, Instant createdAt) {

    @Override
    public String toString() {
        return "UserCreatedEvent{" +
                "\nid = '" + id + '\'' +
                ",\nfirstName = '" + firstName + '\'' +
                ",\nlastName = '" + lastName + '\'' +
                ",\nemail = '" + email + '\'' +
                ",\ncreatedAt = " + createdAt +
                '}';
    }
}
