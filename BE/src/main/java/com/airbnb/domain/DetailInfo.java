package com.airbnb.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class DetailInfo {

    private int maxNumber;
    private String type;
    private String roomIntroduction;
    private double rate;
    private int commentCount;

    public DetailInfo(int maxNumber, String type, String roomIntroduction, double rate, int commentCount) {
        this.maxNumber = maxNumber;
        this.type = type;
        this.roomIntroduction = roomIntroduction;
        this.rate = rate;
        this.commentCount = commentCount;
    }

    public DetailInfo() {
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public String getType() {
        return type;
    }

    public String getRoomIntroduction() {
        return roomIntroduction;
    }

    public double getRate() {
        return rate;
    }

    public int getCommentCount() {
        return commentCount;
    }
}
