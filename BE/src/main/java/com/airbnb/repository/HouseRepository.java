package com.airbnb.repository;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.airbnb.api.houses.dto.NumberOfHousesByPrice;
import com.airbnb.domain.House;

public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("SELECT NEW com.airbnb.api.houses.dto.NumberOfHousesByPrice(FUNCTION('FLOOR', (h.price / 10000)), count(h.id)) "
        + "FROM House h "
        + "WHERE FUNCTION('ST_Distance_Sphere', h.point, :point) < :distance "
        + "GROUP BY FUNCTION('FLOOR', (h.price / 10000)) "
        + "ORDER BY FUNCTION('FLOOR', (h.price / 10000))")
    List<NumberOfHousesByPrice> numberOfHousesInTheRange(@Param("point") Point point, @Param("distance") int distance);

    @Query("SELECT h " +
            "FROM House h " +
            "WHERE FUNCTION('ST_Distance_Sphere', h.point, :point) < :distance AND h.price >= :min AND h.price <= :max")
    List<House> searchByCondition(@Param("point") Point point, @Param("distance") int distance, @Param("min") int min, @Param("max") int max);
}
