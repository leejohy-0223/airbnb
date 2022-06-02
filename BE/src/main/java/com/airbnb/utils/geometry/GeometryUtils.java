package com.airbnb.utils.geometry;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class GeometryUtils {

    public static Point toPoint(Double latitude, Double longitude) {
        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
        try {
            return (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            throw new IllegalStateException("Point 변환과정에서 예외가 발생하였습니다.");
        }
    }
}
