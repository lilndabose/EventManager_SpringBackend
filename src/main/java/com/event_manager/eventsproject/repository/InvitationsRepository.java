package com.event_manager.eventsproject.repository;

import com.event_manager.eventsproject.entity.Invitations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationsRepository extends JpaRepository<Invitations, Long> {
    List<Invitations> findByStatus(String status);
    List<Invitations> findByUserEmail(String email);
    List<Invitations> findByEventIdAndStatus(Long eventId, String status);
    Invitations findByUserEmailAndEventId(String userEmail, Long eventId);
}


