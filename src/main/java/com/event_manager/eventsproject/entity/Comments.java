package com.event_manager.eventsproject.entity;

import jakarta.persistence.*;

@Entity
public class Comments {

    public Comments(Long id, Long userId, Long eventId, String message, String date, String username) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.date = date;
        this.eventId = eventId;
        this.username = username;
    }

    public Comments(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Long userId;
    private Long eventId;
    private String date;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

