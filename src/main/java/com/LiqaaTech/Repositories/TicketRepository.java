package com.LiqaaTech.Repositories;

import com.LiqaaTech.Entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByRegistrationUserId(Long userId);
    Page<Ticket> findByRegistrationUserId(Long userId, Pageable pageable);
    List<Ticket> findByRegistrationEventId(Long eventId);
    Page<Ticket> findByRegistrationEventId(Long eventId, Pageable pageable);
    List<Ticket> findByEventId(Long eventId);
    List<Ticket> findByUserId(Long userId);
    
    // Find tickets by status
    List<Ticket> findByStatus(String status);
    
    // Find tickets by event and status
    List<Ticket> findByEventIdAndStatus(Long eventId, String status);
    
    // Find tickets by user and event
    List<Ticket> findByUserIdAndEventId(Long userId, Long eventId);
    
    // Find tickets by ticket number
    Ticket findByTicketNumber(String ticketNumber);
    
    // Find tickets by ticket code
    Ticket findByTicketCode(String ticketCode);
    
    // Find tickets by QR code
    Ticket findByQrCode(String qrCode);
    
    // Find tickets by barcode
    Ticket findByBarcode(String barcode);
    
    // Find tickets by event and ticket type
    List<Ticket> findByEventIdAndTicketType(Long eventId, String ticketType);
    
    // Find tickets that are used
    List<Ticket> findByIsUsedTrue();
    
    // Find tickets that are unused
    List<Ticket> findByIsUsedFalse();
    
    // Count used tickets by event
    Long countByEventIdAndIsUsedTrue(Long eventId);

    // Count unused tickets by event
    Long countByEventIdAndIsUsedFalse(Long eventId);

    // Count tickets by status
    Long countByStatus(String status);

    // Check existence of ticket by barcode
    boolean existsByBarcode(String barcode);

    // Check existence of ticket by ticket number
    boolean existsByTicketNumber(String ticketNumber);

    // Check existence of ticket by ticket code
    boolean existsByTicketCode(String ticketCode);

    // Check existence of ticket by QR code
    boolean existsByQrCode(String qrCode);

    // Count used tickets by event and status
    Long countByRegistrationEventIdAndStatus(Long eventId, String status);
    
    // Find tickets by check-in status
    List<Ticket> findByCheckInTimeNotNull();
    
    // Find tickets by cancellation status
    List<Ticket> findByCancellationTimeNotNull();
    
    // Find tickets by refund status
    List<Ticket> findByRefundStatus(String refundStatus);
}