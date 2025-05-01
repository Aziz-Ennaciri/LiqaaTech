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
}