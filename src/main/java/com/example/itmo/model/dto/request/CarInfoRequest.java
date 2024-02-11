package com.example.itmo.model.dto.request;

import com.example.itmo.model.enums.CarType;
import com.example.itmo.model.enums.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarInfoRequest {
    String brand;
    String model;
    Color color;
    Integer year;
    Long price;
    Boolean isNew;
    CarType type;
}
