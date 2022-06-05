package com.airbnb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.airbnb.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r"
        + " join fetch r.user u"
        + " join fetch r.house h"
        + " left join fetch h.images"
        + " where u.email = :userEmail")
    List<Reservation> findReservationsByEmail(@Param("userEmail") String userEmail);

    @Query("select r from Reservation r"
        + " join fetch r.house h"
        + " join fetch h.host"
        + " left join fetch h.images"
        + " where r.id = :id")
    @NonNull
    Optional<Reservation> findById(@NonNull @Param("id") Long id);
}
