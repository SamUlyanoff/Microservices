package ru.microservices.user_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user_password")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPassword {

    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "user_password")
    private String hashedPassword;

}
