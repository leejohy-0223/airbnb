package com.airbnb.repository;

import com.airbnb.api.search.dto.SearchConditionRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.airbnb.domain.House;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findByCondition(SearchConditionRequest request, Pageable pageable);
}
