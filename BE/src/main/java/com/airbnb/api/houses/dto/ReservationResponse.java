package com.airbnb.api.houses.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import com.airbnb.domain.Reservation;

public class ReservationResponse {

    // * 반환 : 숙소 이름, 사진, 체크인, 체크아웃, 호스트 정보, 가격 반환
    private Long reservationId;
    private String houseName;
    private String mainImageURL;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    public ReservationResponse(Reservation reservation) {
        this.reservationId = reservation.getId();
        this.houseName = reservation.getHouseName();
        this.mainImageURL = reservation.getMainImageURL();
        this.checkInTime = reservation.getStartDate();
        this.checkOutTime = reservation.getEndDate();
    }

    public Long getReservationId() {
        return reservationId;
    }

    public String getHouseName() {
        return houseName;
    }

    public String getMainImageURL() {
        return mainImageURL;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ReservationResponse that = (ReservationResponse)o;
        return Objects.equals(reservationId, that.reservationId) && Objects.equals(houseName,
            that.houseName) && Objects.equals(mainImageURL, that.mainImageURL) && Objects.equals(
            checkInTime, that.checkInTime) && Objects.equals(checkOutTime, that.checkOutTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, houseName, mainImageURL, checkInTime, checkOutTime);
    }
}
