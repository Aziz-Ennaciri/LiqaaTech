package com.LiqaaTech.DTOs;

import com.LiqaaTech.Enums.RegistrationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for Registration information")
public class RegistrationDTO {

    @NotNull(message = "Participant information is required")
    @Schema(description = "Participant information")
    private UserDTO participantDTO;

    @NotNull(message = "Event information is required")
    @Schema(description = "Event information")
    private EventDTO eventDTO;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Registration date and time")
    private LocalDateTime registeredAt;

    @Schema(description = "Whether the registration is in waiting list", defaultValue = "false")
    private boolean inWaitingList = false;

    @Schema(description = "Ticket information")
    private TicketDTO ticketDTO;

    @Schema(description = "Attendance status")
    private String attendanceStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Check-in time")
    private LocalDateTime checkInTime;

    @Builder.Default
    private RegistrationStatus status = RegistrationStatus.PENDING;

    private String notes;
}