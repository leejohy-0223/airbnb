package com.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airbnb.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
