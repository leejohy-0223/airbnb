package com.airbnb.api.houses.dto;

import java.util.Objects;

import org.locationtech.jts.geom.Point;

import com.airbnb.utils.geometry.GeometryUtils;

public class LocationInformationRequest {

    private Double longitude;
    private Double latitude;

    public Point getPoint() {
        return GeometryUtils.toPoint(longitude, latitude);
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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
