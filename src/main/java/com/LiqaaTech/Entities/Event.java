package com.LiqaaTech.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String title;

    @Size(max = 1000)
    @Column(nullable = false)
    private String description;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false)
    private String location;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer capacity;

    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    @Future
    @Column(name = "start_date_time", columnDefinition = "DATETIME")
    private LocalDateTime startDateTime;

    @Column(name = "deleted")
    private Boolean deleted;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;

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