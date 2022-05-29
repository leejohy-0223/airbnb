package com.airbnb.service;

import com.airbnb.api.houses.dto.HouseDetailResponse;
import com.airbnb.api.houses.dto.SearchConditionRequest;
import com.airbnb.domain.DetailInfo;
import com.airbnb.domain.House;
import com.airbnb.domain.Role;
import com.airbnb.domain.User;
import com.airbnb.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    Point nowPosition;

    @BeforeEach
    void init() throws ParseException {
        Double latitude = 37.549214;
        Double longitude = 126.914016;
        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);

        // WKTReader를 통해 WKT를 실제 타입으로 변환합니다.
        nowPosition = (Point)new WKTReader().read(pointWKT);
    }

    @Test
    public void position_search_test() {

        User host = new User("user1", "email", Role.HOST);
        userRepository.save(host);

        // given
        House house1 = new House("house1", 55000, new DetailInfo(10, "oneRoom1", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(37.5492, 126.9113), host);
        houseService.save(house1); // 1km 내부 거리

        House house2 = new House("house2", 65000, new DetailInfo(10, "oneRoom2", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(37.549391, 126.911755), host);
        houseService.save(house2); // 1km 내부 거리

        House house3 = new House("house3", 75000, new DetailInfo(10, "oneRoom3", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(37.549421, 126.912656), host);
        houseService.save(house3); // 1km 내부 거리

        House house4 = new House("house4", 85000, new DetailInfo(10, "oneRoom4", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(37.552469, 126.933644), host);
        houseService.save(house4); // 1km 보다 먼 거리

        House house5 = new House("house5", 95000, new DetailInfo(10, "oneRoom5", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(37.552469, 126.933650), host);
        houseService.save(house5); // 1km 보다 먼 거리

        // when
        List<HouseDetailResponse> houseDetailResponseList = houseService.findByCondition(
            new SearchConditionRequest(37.549214, 126.914016, 1000, 80000));

        // then
        assertThat(houseDetailResponseList.size()).isEqualTo(3);
        assertThat(houseDetailResponseList).extracting("name").contains("house1", "house2", "house3");
    }
}
