package com.event_manager.eventsproject.repository;
import com.event_manager.eventsproject.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends JpaRepository<Events,Long> {
}
