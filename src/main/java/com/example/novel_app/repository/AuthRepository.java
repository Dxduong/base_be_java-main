package com.example.novel_app.repository;

import com.example.novel_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {
    Optional<User> findByFullNameOrEmail(String fullName, String email);
    User findByEmail(String email);
    User findByFullName(String fullName);
}
