package com.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airbnb.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
