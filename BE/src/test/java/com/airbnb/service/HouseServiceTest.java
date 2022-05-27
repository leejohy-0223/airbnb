package com.airbnb.service;

import com.airbnb.domain.DetailInfo;
import com.airbnb.domain.House;
import com.airbnb.utils.GeometryUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class HouseServiceTest {

    @Autowired
    private HouseService houseService;

    Point nowPosition;

    @BeforeEach
    void init() throws ParseException {
        Double latitude = 37.549214;
        Double longitude = 126.914016;
        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);

        // WKTReader를 통해 WKT를 실제 타입으로 변환합니다.
        nowPosition = (Point) new WKTReader().read(pointWKT);
    }

    @Test
    public void position_search_test() {
        // given
        House house1 = new House("house1", 55000, new DetailInfo(10, "oneRoom1", "방입니다", 4.8, 10), GeometryUtils.toPoint(37.5492, 126.9113));
        houseService.save(house1); // 1km 내부 거리

        House house2 = new House("house2", 65000, new DetailInfo(10, "oneRoom2", "방입니다", 4.8, 10), GeometryUtils.toPoint(37.549391, 126.911755));
        houseService.save(house2); // 1km 내부 거리

        House house3 = new House("house3", 75000, new DetailInfo(10, "oneRoom3", "방입니다", 4.8, 10), GeometryUtils.toPoint(37.549421, 126.912656));
        houseService.save(house3); // 1km 내부 거리

        House house4 = new House("house4", 85000, new DetailInfo(10, "oneRoom4", "방입니다", 4.8, 10), GeometryUtils.toPoint(37.552469, 126.933644));
        houseService.save(house4); // 1km 보다 먼 거리

        House house5 = new House("house5", 95000, new DetailInfo(10, "oneRoom5", "방입니다", 4.8, 10), GeometryUtils.toPoint(37.552469, 126.933650));
        houseService.save(house5); // 1km 보다 먼 거리

        // when
        List<House> houseList = houseService.findByCondition(nowPosition, 1000, 80000);

        // then
        assertThat(houseList.size()).isEqualTo(3);
        assertThat(houseList).extracting("name").contains("house1", "house2", "house3");
    }
}
