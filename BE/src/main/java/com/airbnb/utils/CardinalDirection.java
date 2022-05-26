package com.airbnb.utils;

public enum CardinalDirection {
    NORTH(0.0), WEST(270.0), SOUTH(180.0), EAST(90.0),
    NORTH_WEST(315.0), SOUTH_WEST(225.0), SOUTH_EAST(135.0), NORTH_EAST(45.0);

    private final Double bearing;

    CardinalDirection(Double bearing) {
        this.bearing = bearing;
    }

    public Double getBearing() {
        return bearing;
    }
}
