package com.airbnb.domain.login;

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
}
