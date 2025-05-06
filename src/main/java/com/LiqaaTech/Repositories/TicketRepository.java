package com.LiqaaTech.Repositories;

import com.LiqaaTech.Entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByRegistration_Event_IdAndRegistration_Id(Long eventId, Long registrationId);
    
    @Query("SELECT t FROM Ticket t WHERE t.registration.event.id = :eventId")
    List<Ticket> findByEventId(@Param("eventId") Long eventId);
    
    @Query("SELECT t FROM Ticket t WHERE t.registration.participant.id = :userId")
    List<Ticket> findByUserId(@Param("userId") Long userId);
}