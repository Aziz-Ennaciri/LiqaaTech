package com.LiqaaTech.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Event extends EntityBase {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    @NotNull
    private User organizer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Registration> registrations;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Ticket> tickets;

    @PrePersist
    protected void onCreate() {
        if (startDateTime == null) {
            startDateTime = LocalDateTime.now().plusDays(1);
        }
        if (deleted == null) {
            deleted = false;
        }
        if (registrations == null) {
            registrations = new HashSet<>();
        }
        if (tickets == null) {
            tickets = new HashSet<>();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (startDateTime != null && startDateTime.isBefore(LocalDateTime.now())) {
            startDateTime = LocalDateTime.now().plusDays(1);
        }
    }

    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotNull
    @Future
    @Column(name = "start_date_time", columnDefinition = "DATETIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDateTime;

    @Future
    @Column(name = "end_date_time", columnDefinition = "DATETIME", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDateTime;

    @Column(name = "deleted")
    private Boolean deleted;

    @NotBlank
    @Size(max = 200)
    private String location;

    public LocalDateTime getStartDateTime() {
        if (startDateTime == null || startDateTime.isBefore(LocalDateTime.now())) {
            return LocalDateTime.now().plusDays(1).withMinute(0).withSecond(0);
        }
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        if (startDateTime == null) {
            this.startDateTime = LocalDateTime.now().plusDays(1);
        } else if (startDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event start date must be in the future");
        }
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        if (endDateTime == null) {
            this.endDateTime = LocalDateTime.now().plusDays(1).withHour(23).withMinute(59).withSecond(59);
        } else if (endDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event end date must be in the future");
        }
        this.endDateTime = endDateTime;
    }

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;

    @NotNull
    @Min(1)
    private Integer capacity;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @Lob
    @Column(name = "event_image", length = 1000000)
    private byte[] eventImage;

    @Transient
    private String eventImageBase64;

    @Transient
    private String eventImageContentType;

    @Transient
    private Long waitingListCount;

    @Transient
    private Integer totalRegistrations;

    @Transient
    private Integer paidRegistrations;

    @Transient
    private Integer freeRegistrations;

    public int getAvailableSpots() {
        return capacity - registrations.size();
    }
}
