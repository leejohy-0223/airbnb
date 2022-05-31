package com.airbnb.repository;

import com.airbnb.domain.DetailInfo;
import com.airbnb.domain.House;
import com.airbnb.domain.Role;
import com.airbnb.domain.User;
import com.airbnb.repository.dto.HouseCount;
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
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class HouseRepositoryTest {

    @Autowired
    HouseRepository houseRepository;
    @Autowired
    UserRepository userRepository;

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
    public void search_condition_jpql_test() {
        // given
        User host = new User("user1", "email", Role.HOST);
        userRepository.save(host);

        House house1 = new House("house1", 55000, new DetailInfo(10, "oneRoom1", "방입니다", 4.8, 10),
                GeometryUtils.toPoint(37.5492, 126.9113), host);

        House house2 = new House("house2", 55000, new DetailInfo(10, "oneRoom1", "방입니다", 4.8, 10),
                GeometryUtils.toPoint(37.5492, 126.9113), host);

        House house3 = new House("house3", 55000, new DetailInfo(10, "oneRoom1", "방입니다", 4.8, 10),
                GeometryUtils.toPoint(37.5492, 126.9113), host);

        House house4 = new House("house4", 85000, new DetailInfo(10, "oneRoom4", "방입니다", 4.8, 10),
                GeometryUtils.toPoint(37.552469, 126.933644), host);

        House house5 = new House("house5", 95000, new DetailInfo(10, "oneRoom5", "방입니다", 4.8, 10),
                GeometryUtils.toPoint(37.552469, 126.933650), host);

        houseRepository.saveAll(List.of(house1, house2, house3, house4, house5)); // 1km 보다 먼 거리

        // when
        List<House> result = houseRepository.searchByCondition(nowPosition, 1000, 10, 100000);

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).extracting("name").contains("house1", "house2", "house3");
    }
}
