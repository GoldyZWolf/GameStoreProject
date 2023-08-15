package com.example.security.full.security.users.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity(name = "User")
@Table(name = "user", uniqueConstraints = @UniqueConstraint(name = "user_email_unique", columnNames = "email"))
public class User {
    @Id
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_seq"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "roles", nullable = false)
    private String Roles;

    @ToString.Exclude
    @Column(name = "password", nullable = false, columnDefinition = "text")
    private String password;


}
