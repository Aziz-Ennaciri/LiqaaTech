package com.LiqaaTech.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for creating a new Event")
public class EventCreateDTO {
    @NotBlank
    @Size(max = 100)
    @Schema(description = "Title of the event", example = "Concert Night")
    private String title;

    @NotBlank
    @Size(max = 1000)
    @Schema(description = "Description of the event", example = "Live concert featuring local artists")
    private String description;

    @NotBlank
    @Size(max = 200)
    @Schema(description = "Location of the event", example = "Central Park")
    private String location;

    @NotNull
    @Min(1)
    @Schema(description = "Maximum number of tickets available", example = "100")
    private Integer capacity;

    @NotNull
    @DecimalMin("0.01")
    @Schema(description = "Price per ticket", example = "50.00")
    private BigDecimal price;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Schema(description = "Start date and time of the event", example = "2025-05-30T20:00:00")
    private LocalDateTime startDateTime;

    @NotNull
    @Schema(description = "Category ID for the event", example = "1")
    private Long category;

    @Schema(description = "Organizer ID for the event", example = "1")
    private Long organizer;
}