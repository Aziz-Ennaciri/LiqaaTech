package com.LiqaaTech.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.LiqaaTech.Enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for User information")
public class UserDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "User email address", example = "user@example.com")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "Username", example = "john_doe")
    private String username;

    @NotNull(message = "Role is required")
    @Schema(description = "User role")
    private Role role;

    @Schema(description = "Whether the user account is enabled", defaultValue = "true")
    private boolean enabled;

    @Builder.Default
    @Schema(description = "List of events organized by the user")
    private List<EventDTO> organizedEventsDTO = new ArrayList<>();

    @Builder.Default
    @Schema(description = "List of event registrations")
    private List<RegistrationDTO> registrationsDTO = new ArrayList<>();

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    @Schema(description = "User phone number", example = "+1234567890")
    private String phoneNumber;

    @Pattern(regexp = "^(http|https)://.*$", message = "Profile picture URL must be a valid URL")
    @Schema(description = "URL of the user's profile picture")
    private String profilePictureUrl;
}