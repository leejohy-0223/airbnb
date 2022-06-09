package com.airbnb.api.houses.dto;

import java.time.LocalDateTime;

import com.airbnb.domain.Reservation;

public class ReservationDetailResponse {

    private Long reservationId;
    private String houseName;
    private String mainImageURL;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    private String hostName;
    private int numberOfGuests;
    private int fee;

    public ReservationDetailResponse(Reservation reservation) {
        this.reservationId = reservation.getId();
        this.houseName = reservation.getHouseName();
        this.mainImageURL = reservation.getMainImageURL();
        this.checkInTime = reservation.getStartDate();
        this.checkOutTime = reservation.getEndDate();
        this.hostName = reservation.getHostName();
        this.houseName = reservation.getHouseName();
        this.numberOfGuests = reservation.getNumberOfGuests();
        this.fee = reservation.getFee();
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

    public String getHostName() {
        return hostName;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public int getFee() {
        return fee;
    }
}
