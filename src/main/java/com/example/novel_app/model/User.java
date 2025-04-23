package com.example.novel_app.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "account")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String fullName;
    String password;
    String email;
    @Column(name = "roles")
    @ElementCollection(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> roles;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;
}
