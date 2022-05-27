package com.airbnb.repository;

import com.airbnb.domain.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long>, HouseCustomRepository {
}
