package com.airbnb.api.houses.dto;

import com.airbnb.utils.GeometryUtils;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

import javax.persistence.criteria.CriteriaBuilder;

public class SearchConditionRequest {

    private Double longitude;
    private Double latitude;
    private LocalDateTime startLocalDateTime;
    private LocalDateTime endLocalDateTime;
    private Integer minFee;
    private Integer maxFee;
    private int numberOfGuests;

    public SearchConditionRequest(Double longitude, Double latitude, Integer minFee, Integer maxFee) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.minFee = minFee;
        this.maxFee = maxFee;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Point getPoint() {
        return GeometryUtils.toPoint(longitude, latitude);
    }

    public LocalDateTime getStartLocalDateTime() {
        return startLocalDateTime;
    }

    public void setStartLocalDateTime(LocalDateTime startLocalDateTime) {
        this.startLocalDateTime = startLocalDateTime;
    }

    public LocalDateTime getEndLocalDateTime() {
        return endLocalDateTime;
    }

    public void setEndLocalDateTime(LocalDateTime endLocalDateTime) {
        this.endLocalDateTime = endLocalDateTime;
    }

    public Integer getMinFee() {
        return minFee;
    }

    public void setMinFee(Integer minFee) {
        this.minFee = minFee;
    }

    public Integer getMaxFee() {
        return maxFee;
    }

    public void setMaxFee(Integer maxFee) {
        this.maxFee = maxFee;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

}
