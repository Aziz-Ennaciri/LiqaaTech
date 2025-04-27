package com.LiqaaTech.Repositories;

import com.LiqaaTech.Entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEventIdAndRegistrationId(Long eventId, Long registrationId);
}
