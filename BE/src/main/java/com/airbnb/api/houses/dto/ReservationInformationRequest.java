package com.airbnb.api.houses.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

public class ReservationInformationRequest {

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDateTime;

    private int numberOfGuests;
    private int fee;

    public ReservationInformationRequest(LocalDateTime startDateTime, LocalDateTime endDateTime, int numberOfGuests,
        int fee) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.numberOfGuests = numberOfGuests;
        this.fee = fee;
    }

    public ReservationInformationRequest() {
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public int getFee() {
        return fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ReservationInformationRequest that = (ReservationInformationRequest)o;
        return numberOfGuests == that.numberOfGuests && fee == that.fee && Objects.equals(startDateTime,
            that.startDateTime) && Objects.equals(endDateTime, that.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, endDateTime, numberOfGuests, fee);
    }
}
