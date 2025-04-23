package com.example.novel_app.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String email;
    String otpCode;
    boolean statusOtp = false;

    @Column(name = "expired_at", updatable = false)
    LocalDateTime expiredAt;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @PrePersist
    public void setDefaultExpiredAt() {
        if (expiredAt == null) {
            expiredAt = LocalDateTime.now().plusMinutes(2);
        }
    }
}
