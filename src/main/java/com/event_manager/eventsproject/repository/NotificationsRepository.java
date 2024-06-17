package com.event_manager.eventsproject.repository;

import com.event_manager.eventsproject.entity.Invitations;
import com.event_manager.eventsproject.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
    List<Notifications> findByUserId(Long userId);

    @Query(value = "SELECT * FROM notifications where context = :context1 or context = :context2",nativeQuery = true)
    List<Notifications> getAllUsersNotifications(@Param("context1") String context1,@Param("context2") String context2);
}


