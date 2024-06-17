package com.event_manager.eventsproject.repository;

import com.event_manager.eventsproject.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByUserId(Long userId);

    @Query(value = "SELECT * FROM comments where event_id = :eventId",nativeQuery = true)
    List<Comments> findAllEventsById(@Param("eventId") Long eventId);
    List<Comments> findByUserIdAndEventId(Long userId, Long eventId);
}


