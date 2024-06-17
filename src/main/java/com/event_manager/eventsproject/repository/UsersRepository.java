package com.event_manager.eventsproject.repository;

import com.event_manager.eventsproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query(value = "SELECT * FROM users WHERE (username = :username or email = :username) and password = :password",nativeQuery = true)
    Users loginUser(@Param("username") String username,  @Param("password") String password);

    @Query(value = "SELECT * FROM users WHERE username = :username or email = :email",nativeQuery = true)
    Users usernameOrEmailExist(@Param("username") String username, @Param("email") String email);

    Users findByEmail(String email);
}


