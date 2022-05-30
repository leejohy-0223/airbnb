package com.airbnb.api.houses.dto;

import org.locationtech.jts.geom.Point;

import com.airbnb.utils.GeometryUtils;

public class LocationInformationRequest {

    private Double longitude;
    private Double latitude;

    public LocationInformationRequest(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Point getPoint() {
        return GeometryUtils.toPoint(longitude, latitude);
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
