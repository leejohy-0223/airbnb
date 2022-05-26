package com.airbnb.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
@Access(AccessType.FIELD)
public class Position {
    private double xPosition;
    private double yPosition;
}
