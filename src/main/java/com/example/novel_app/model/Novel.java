package com.example.novel_app.model;

import ch.qos.logback.core.model.INamedModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="novel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Novel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    Integer authorId;
    Integer genreId;
    String title;
    String description;
    @Column(name = "image_url")
    String imageUrl;
    String status;
    int rate;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}
