package com.LiqaaTech.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for Event information")
public class EventDTO {

    @Schema(description = "Event ID")
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    @Schema(description = "Event title", example = "Tech Conference 2024")
    private String title;

    @NotBlank(message = "Location is required")
    @Schema(description = "Event location", example = "Convention Center, New York")
    private String location;

    @NotNull(message = "Date is required")
    @Future(message = "Event date must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Event date and time", example = "2024-12-31 15:00:00")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date is required")
    @Future(message = "Event end date must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Event end date and time", example = "2024-12-31 17:00:00")
    private LocalDateTime endDateTime;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    @Schema(description = "Event description")
    private String description;

    @Pattern(regexp = "^(http|https)://.*$", message = "Image URL must be a valid URL")
    @Schema(description = "URL of the event image")
    private String imageUrl;

    @Schema(description = "Event capacity", example = "100")
    private Integer capacity;

    @Schema(description = "Event price", example = "50.00")
    private Double price;

    @Schema(description = "Whether the event is active", defaultValue = "true")
    private Boolean isActive = true;

    @NotNull(message = "Organizer information is required")
    @Schema(description = "Event organizer ID")
    private Long organizerId;

    @Schema(description = "Event organizer name")
    private String organizerName;

    @Schema(description = "List of event categories")
    private List<CategoryDTO> categories;

    @Builder.Default
    @Schema(description = "List of registrations for the event")
    private List<RegistrationDTO> registrations = new ArrayList<>();
}