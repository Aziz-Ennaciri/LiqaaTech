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
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for Event information")
public class EventDTO {

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
    private LocalDateTime date;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    @Schema(description = "Event description")
    private String description;

    @Pattern(regexp = "^(http|https)://.*$", message = "Image URL must be a valid URL")
    @Schema(description = "URL of the event image")
    private String imageUrl;

    @Schema(description = "Whether the event is public", defaultValue = "true")
    private boolean isPublic = true;

    @Schema(description = "Event category", example = "Technology")
    private String category;

    @NotNull(message = "Organizer information is required")
    @Schema(description = "Event organizer information")
    private UserDTO organizerDTO;

    @Builder.Default
    @Schema(description = "List of registrations for the event")
    private List<RegistrationDTO> registrationsDTO = new ArrayList<>();

    @Min(value = 1, message = "Maximum participants must be at least 1")
    @Schema(description = "Maximum number of participants allowed")
    private Integer maxParticipants;

    @Future(message = "Registration deadline must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Registration deadline")
    private LocalDateTime registrationDeadline;
}