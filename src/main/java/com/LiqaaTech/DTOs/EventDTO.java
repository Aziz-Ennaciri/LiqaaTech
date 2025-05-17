package com.LiqaaTech.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for Event information")
public class EventDTO {
    private Long id;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;
    private String location;
    private Integer maxTickets;
    private BigDecimal price;

    private Long category;
    private Long organizer;
    private Integer availableSpots;

    public LocalDateTime getStartDateTime() {
        return dateTime;
    }

    public void setStartDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getCapacity() {
        return maxTickets;
    }

    public void setCapacity(Integer capacity) {
        this.maxTickets = capacity;
    }

    public BigDecimal getTicketPrice() {
        return price;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.price = ticketPrice;
    }
}