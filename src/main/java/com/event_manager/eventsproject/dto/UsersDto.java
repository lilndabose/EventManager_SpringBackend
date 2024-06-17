package com.event_manager.eventsproject.dto;

public class UsersDto {
    private Long id;
    private String username;
    private String email;

    public UsersDto(Long id,String email, String username) {
        this.email = email;
        this.username = username;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
