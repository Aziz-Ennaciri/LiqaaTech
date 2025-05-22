package com.LiqaaTech.Repositories;

import com.LiqaaTech.Entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    @Query("SELECT r FROM Registration r LEFT JOIN FETCH r.user u LEFT JOIN FETCH r.event e WHERE r.user.id = :userId")
    List<Registration> findByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Registration r LEFT JOIN FETCH r.user u LEFT JOIN FETCH r.event e WHERE r.event.id = :eventId")
    List<Registration> findByEventId(@Param("eventId") Long eventId);

    @Query("SELECT r FROM Registration r LEFT JOIN FETCH r.user u LEFT JOIN FETCH r.event e WHERE r.user.id = :userId")
    List<Registration> findByUserIdWithDetails(@Param("userId") Long userId);

    @Query("SELECT r FROM Registration r LEFT JOIN FETCH r.user u LEFT JOIN FETCH r.event e WHERE r.event.id = :eventId")
    List<Registration> findByEventIdWithDetails(@Param("eventId") Long eventId);

    @Query("SELECT r FROM Registration r LEFT JOIN FETCH r.user u LEFT JOIN FETCH r.event e")
    List<Registration> findAllWithDetails();

    @Query("SELECT r FROM Registration r LEFT JOIN FETCH r.user u LEFT JOIN FETCH r.event e WHERE r.id = :id")
    Optional<Registration> findByIdWithDetails(@Param("id") Long id);
}
