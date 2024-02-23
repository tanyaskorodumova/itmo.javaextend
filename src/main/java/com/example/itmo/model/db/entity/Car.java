package com.example.itmo.model.db.entity;

import com.example.itmo.model.enums.CarStatus;
import com.example.itmo.model.enums.CarType;
import com.example.itmo.model.enums.Color;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cars")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String brand;
    String model;

    @Enumerated(EnumType.STRING)
    Color color;

    Integer year;
    Long price;

    @Column(name = "is_new")
    Boolean isNew;

    @Enumerated(EnumType.STRING)
    CarType type;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    CarStatus status;

    @ManyToOne
    @JsonBackReference(value = "driver_cars")
    User user;
}
