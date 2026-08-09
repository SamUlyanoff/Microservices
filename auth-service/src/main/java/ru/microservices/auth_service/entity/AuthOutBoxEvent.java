package ru.microservices.auth_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ru.microservices.common_events.outbox.AbstractOutBoxEvent;

@Entity
@Table(name = "outbox_auth")
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
public class AuthOutBoxEvent extends AbstractOutBoxEvent {

}
