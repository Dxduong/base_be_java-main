package com.example.novel_app.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_novel")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentNovel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int novelId;
    int userId;
    @Column(columnDefinition = "TEXT")
    String content;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;
}
