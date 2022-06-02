package com.airbnb.api.houses.dto;

import java.util.Objects;

import org.locationtech.jts.geom.Point;

import com.airbnb.utils.geometry.GeometryUtils;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LocationInformationRequest that = (LocationInformationRequest)o;
        return Objects.equals(getLongitude(), that.getLongitude()) && Objects.equals(getLatitude(),
            that.getLatitude());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLongitude(), getLatitude());
    }
}
