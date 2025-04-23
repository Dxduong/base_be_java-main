package com.example.novel_app.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "author")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    String authorName;
    String bio;
    @Column(name = "birth_date")
    LocalDate birthDate;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;

}
