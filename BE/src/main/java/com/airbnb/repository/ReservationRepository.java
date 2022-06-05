package com.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airbnb.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
