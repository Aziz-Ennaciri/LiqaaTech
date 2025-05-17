package com.LiqaaTech.Repositories;

import com.LiqaaTech.Entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByCategoryId(Long categoryId, Pageable pageable);
    
    @Query("SELECT e FROM Event e WHERE e.dateTime > :now ORDER BY e.dateTime ASC")
    List<Event> findUpcomingEvents(@Param("now") LocalDateTime now);
    
    @Query("SELECT e FROM Event e WHERE e.dateTime <= :now")
    List<Event> findOngoingEvents(@Param("now") LocalDateTime now);
    
    @Query("SELECT e FROM Event e WHERE e.dateTime < :now ORDER BY e.dateTime DESC")
    List<Event> findPastEvents(@Param("now") LocalDateTime now);

    List<Event> findByDateTimeAfter(LocalDateTime dateTime);
}
