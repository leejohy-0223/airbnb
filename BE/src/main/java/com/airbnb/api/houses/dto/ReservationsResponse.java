package com.airbnb.api.houses.dto;

import java.util.List;
import java.util.Objects;

public class ReservationsResponse {
    private List<ReservationResponse> reservationResponses;

    public ReservationsResponse(List<ReservationResponse> reservationResponses) {
        this.reservationResponses = reservationResponses;
    }

    public List<ReservationResponse> getReservationResponses() {
        return reservationResponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ReservationsResponse that = (ReservationsResponse)o;
        return Objects.equals(reservationResponses, that.reservationResponses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationResponses);
    }
}
