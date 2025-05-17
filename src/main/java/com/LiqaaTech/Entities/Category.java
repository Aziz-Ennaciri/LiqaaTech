package com.LiqaaTech.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper = false)
public class Category extends EntityBase {
    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String name;

    @Size(max = 500)
    private String description;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Event> events = new HashSet<>();
}