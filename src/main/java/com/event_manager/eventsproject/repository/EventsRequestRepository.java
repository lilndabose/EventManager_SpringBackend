package com.event_manager.eventsproject.repository;

import com.event_manager.eventsproject.entity.EventsRequest;
import com.event_manager.eventsproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRequestRepository extends JpaRepository<EventsRequest, Long> {
    @Query(value = "SELECT * FROM events_table WHERE user_id = :user_id",nativeQuery = true)
    List<EventsRequest> getAllUsersRequests(@Param("user_id") Long user_id);

    @Query(value = "SELECT * FROM events_table WHERE user_id = :user_id AND event_id = :event_id",nativeQuery = true)
    List<EventsRequest> requestExist(@Param("user_id") Long user_id, @Param("event_id") Long event_id);

}


