package com.LiqaaTech.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for Ticket information")
public class TicketDTO {

    @NotBlank(message = "Ticket code is required")
    @Schema(description = "Unique ticket code")
    private String ticketCode;

    @Schema(description = "Registration information")
    private RegistrationDTO registrationDTO;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Ticket validity period")
    private LocalDateTime validUntil;

    @Schema(description = "Whether the ticket has been used")
    private boolean isUsed;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "When the ticket was used")
    private LocalDateTime usedAt;
}