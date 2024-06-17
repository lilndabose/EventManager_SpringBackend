package com.event_manager.eventsproject.entity;
import jakarta.persistence.*;


@Entity
@Table(name = "events_table")
public class EventsRequest {

    public EventsRequest(Long id, Long event_id, Long user_id, String status) {
        this.id = id;
        this.event_id = event_id;
        this.user_id = user_id;
        this.status = status;
    }

    public EventsRequest() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long event_id;
    private Long user_id;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
