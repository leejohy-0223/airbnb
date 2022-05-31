package com.airbnb.repository;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.airbnb.domain.DetailInfo;
import com.airbnb.domain.House;
import com.airbnb.domain.Role;
import com.airbnb.domain.User;
import com.airbnb.repository.dto.HouseCount;
import com.airbnb.utils.GeometryUtils;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
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

        User host = new User("user1", "email", Role.HOST);
        userRepository.save(host);

        House house0 = new House("house0", 85000, new DetailInfo(10, "oneRoom0", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(37.549, 126.928), host);

        House house1 = new House("house1", 55000, new DetailInfo(10, "oneRoom1", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(37.5492, 126.9113), host);

        House house2 = new House("house2", 55000, new DetailInfo(10, "oneRoom2", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(37.5492, 126.9113), host);

        House house3 = new House("house3", 55000, new DetailInfo(10, "oneRoom3", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(37.5492, 126.9113), host);

        House house4 = new House("house4", 85000, new DetailInfo(10, "oneRoom4", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(37.552469, 126.933644), host);

        House house5 = new House("house5", 95000, new DetailInfo(10, "oneRoom5", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(37.552469, 126.933650), host);

        House house6 = new House("house6", 95000, new DetailInfo(10, "oneRoom6", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(37.5493, 126.9199), host);

        houseRepository.saveAll(List.of(house0, house1, house2, house3, house4, house5, house6));
    }

    @DisplayName("반경 1000m 내 10 ~ 100000원의 가격을 가진 숙소를 찾는다.")
    @Test
    void search_condition_jpql_test() {
        // when
        List<House> result = houseRepository.searchByCondition(nowPosition, 1000, 10, 100000);

        // then
        assertThat(result.size()).isEqualTo(4);
        assertThat(result).extracting("name").containsExactly("house1", "house2", "house3", "house6");
    }

    @DisplayName("가격대 별 숙소의 개수를 10000원 단위 오름차순으로 반환한다.")
    @Test
    void number_of_house_test() {
        // when
        List<HouseCount> houseCounts = houseRepository.numberOfHousesInTheRange(nowPosition, 1000);

        // then
        assertThat(houseCounts.size()).isEqualTo(2);
        assertThat(houseCounts.get(0).getPrice()).isEqualTo(50000);
        assertThat(houseCounts.get(0).getCount()).isEqualTo(3);
        assertThat(houseCounts.get(1).getPrice()).isEqualTo(90000);
        assertThat(houseCounts.get(1).getCount()).isEqualTo(1);
    }
}
