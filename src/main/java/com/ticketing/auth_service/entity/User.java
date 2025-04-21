package com.ticketing.auth_service.entity;

import java.sql.Timestamp;
import java.util.UUID;

import com.ticketing.auth_service.dto.UserDTO;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public enum Role {
        ATTENDEE,
        ORGANIZER,
        ADMIN,
        SUPER_ADMIN
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Status {
        ACTIVE,
        INACTIVE,
        BANNED
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Timestamp createdAt;


}
