package com.airbnb.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.airbnb.domain.DetailInfo;
import com.airbnb.domain.House;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class HouseRepositoryTest {

    @Autowired
    private HouseRepository houseRepository;

    @Test
    void save_house_info() {
        // given
        House house = new House("house1", 10000, new DetailInfo(3, "oneRoom", "방입니다", 4.8, 100));

        // when
        House save = houseRepository.save(house);

        // then
        House foundedHouse = houseRepository.findById(save.getId()).get();
        assertThat(foundedHouse.getName()).isEqualTo("house1");
    }

}
