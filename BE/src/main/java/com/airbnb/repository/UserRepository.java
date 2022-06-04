package com.airbnb.repository;

import com.airbnb.domain.User;
import com.airbnb.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    @Query("select w from Wish w" +
            " join fetch w.user u" +
            " join fetch w.house" +
            " where u.email = :userEmail")
    List<Wish> findUserWishListByEmail(@Param("userEmail") String userEmail);
}
