package com.airbnb.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE reservation SET deleted = true WHERE reservation_id = ?")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    private int fee;
    private int numberOfGuests;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    private boolean deleted = false;

    public Reservation(int fee, int numberOfGuests, LocalDateTime startDate, LocalDateTime endDate,
        User user, House house) {
        this.fee = fee;
        this.numberOfGuests = numberOfGuests;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.house = house;
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public int getFee() {
        return fee;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getHouseName() {
        return house.getName();
    }

    public String getMainImageURL() {
        return house.getMainImageURL();
    }

    public String getHostName() {
        return house.getHostName();
    }
}
