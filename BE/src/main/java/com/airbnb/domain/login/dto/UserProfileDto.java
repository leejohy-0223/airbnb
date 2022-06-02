package com.airbnb.domain.login.dto;

import java.util.Objects;

public class UserProfileDto {

    private Long id;
    private String email;
    private String username;

    public UserProfileDto(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return username;
    }

    @Override
    public String toString() {
        return "UserProfileDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserProfileDto that = (UserProfileDto) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getEmail(), that.getEmail())
                && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), username);
    }
}
