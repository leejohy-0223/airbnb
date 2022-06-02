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

import com.airbnb.api.houses.dto.NumberOfHousesByPrice;
import com.airbnb.domain.DetailInfo;
import com.airbnb.domain.House;
import com.airbnb.domain.Role;
import com.airbnb.domain.User;
import com.airbnb.utils.geometry.GeometryUtils;

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

        houseRepository.saveAll(List.of(
            createHouse("house0", 85000, 37.549, 126.928, host),
            createHouse("house1", 55000, 37.5492, 126.9113, host),
            createHouse("house2", 55000, 37.5492, 126.9113, host),
            createHouse("house3", 55000, 37.5492, 126.9113, host),
            createHouse("house4", 85000, 37.552469, 126.933644, host),
            createHouse("house5", 95000, 37.552469, 126.933650, host),
            createHouse("house6", 95000, 37.5493, 126.9199, host)));
    }

    @DisplayName("반경 1000m 내 10 ~ 100000원의 가격을 가진 숙소를 찾는다.")
    @Test
    void search_condition_jpql_test() {
        // when
        int range = 1000;
        int minPrice = 10;
        int maxPrice = 100000;

        List<House> result = houseRepository.searchByCondition(nowPosition, range, minPrice, maxPrice);

        // then
        assertThat(result.size()).isEqualTo(4);
        assertThat(result).extracting("name").containsExactly("house1", "house2", "house3", "house6");
    }

    @DisplayName("가격대 별 숙소의 개수를 10000원 단위 오름차순으로 반환한다.")
    @Test
    void number_of_house_test() {
        // when
        int range = 1000;
        List<NumberOfHousesByPrice> houseCounts = houseRepository.numberOfHousesInTheRange(nowPosition, range);

        // then
        assertThat(houseCounts.size()).isEqualTo(2);
        assertThat(houseCounts.get(0).getPrice()).isEqualTo(50000);
        assertThat(houseCounts.get(0).getCount()).isEqualTo(3);
        assertThat(houseCounts.get(1).getPrice()).isEqualTo(90000);
        assertThat(houseCounts.get(1).getCount()).isEqualTo(1);
    }

    private House createHouse(String houseName, int price, Double latitude, Double longtitude, User host) {
        return new House(houseName, price, new DetailInfo(10, "oneRoom", "방입니다", 4.8, 10),
            GeometryUtils.toPoint(latitude, longtitude), host);
    }
}
