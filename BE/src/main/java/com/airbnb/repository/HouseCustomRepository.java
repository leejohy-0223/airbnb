package com.airbnb.repository;

import com.airbnb.domain.House;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface HouseCustomRepository {

    List<House> searchByConditionNative(Point position, Integer minFee, Integer maxFee);

    List<String> searchByConditionNativeV2(Point position, Integer minFee, Integer maxFee);
}
