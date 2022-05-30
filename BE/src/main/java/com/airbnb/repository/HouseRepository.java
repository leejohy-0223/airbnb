package com.airbnb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.airbnb.domain.House;
import com.airbnb.repository.dto.HouseCount;

public interface HouseRepository extends JpaRepository<House, Long>, HouseCustomRepository {

    /**
     * select FLOOR(t.price / 10000), count(*) from priceTest t group by FLOOR(t.price / 10000);
     * FUNC('FLOOR', MAX(ctr1.version))
     */
    @Query("SELECT NEW com.airbnb.repository.dto.HouseCount(FUNCTION('FLOOR', (h.price / 10000)), count(h.id)) FROM House h GROUP BY FUNCTION('FLOOR', (h.price / 10000))")
    List<HouseCount> numberOfHousesInTheRange();
}
