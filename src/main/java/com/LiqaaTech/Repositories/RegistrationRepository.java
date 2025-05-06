package com.LiqaaTech.Repositories;

import com.LiqaaTech.Entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByEventId(Long eventId);
    
    @Query("SELECT r FROM Registration r WHERE r.participant.id = :userId")
    List<Registration> findByUserId(@Param("userId") Long userId);
}
