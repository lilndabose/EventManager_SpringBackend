package com.event_manager.eventsproject.entity;

import jakarta.persistence.*;

@Entity
public class Invitations {

    public Invitations(Long id, Long eventId, String userEmail, String status) {
        this.id = id;
        this.userEmail = userEmail;
        this.status = status;
        this.eventId = eventId;
    }

    public Invitations(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEmail;
    private Long eventId;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
