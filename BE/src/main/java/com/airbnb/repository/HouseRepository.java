package com.airbnb.repository;

import com.airbnb.domain.House;
import com.airbnb.repository.dto.HouseCount;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("SELECT NEW com.airbnb.repository.dto.HouseCount(FUNCTION('FLOOR', (h.price / 10000)), count(h.id)) FROM House h GROUP BY FUNCTION('FLOOR', (h.price / 10000))")
    List<HouseCount> numberOfHousesInTheRange();

    @Query("SELECT h " +
            "FROM House h " +
            "WHERE FUNCTION('ST_Distance_Sphere', h.point, :point) < :distance AND h.price >= :min AND h.price <= :max")
    List<House> searchByCondition(@Param("point") Point point, @Param("distance") int distance, @Param("min") int min, @Param("max") int max);
}
