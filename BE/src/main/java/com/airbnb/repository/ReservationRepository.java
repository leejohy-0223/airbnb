package com.airbnb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.airbnb.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r From Reservation r"
        + " join fetch r.user u"
        + " join fetch r.house h"
        + " left join fetch h.images"
        + " where u.email = :userEmail")
    List<Reservation> findReservationsByEmail(@Param("userEmail") String userEmail);
}
