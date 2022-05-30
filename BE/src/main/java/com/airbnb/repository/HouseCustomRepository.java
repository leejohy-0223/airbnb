package com.airbnb.repository;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.Query;

import com.airbnb.domain.House;


public interface HouseCustomRepository {

    List<House> searchByCondition(Point position, Integer minFee, Integer maxFee);

    List<House> searchByConditionQueryDsl(Point position, Integer minFee, Integer maxFee);

}
