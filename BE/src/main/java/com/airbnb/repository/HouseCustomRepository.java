package com.airbnb.repository;

import com.airbnb.domain.House;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface HouseCustomRepository {

    List<String> searchByCondition(Point position, Integer minFee, Integer maxFee);

    List<House> searchByConditionQueryDsl(Point position, Integer minFee, Integer maxFee);
}
