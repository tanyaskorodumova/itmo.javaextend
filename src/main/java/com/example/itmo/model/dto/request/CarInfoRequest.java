package com.example.itmo.model.dto.request;

import com.example.itmo.model.enums.CarType;
import com.example.itmo.model.enums.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarInfoRequest {

    @NotEmpty(message = "Brand must be set")
    @Pattern(regexp = "^\\D+$", message = "В поле brand необходимо указать только сам бренд, без модели - только буквы")
    String brand;

    String model;
    Color color;

    @Min(value = 1920, message = "Год выпуска не должен быть меньше 1920")
    Integer year;

    @Positive(message = "Цена не может быть отрицательной")
    Long price;

    Boolean isNew;
    CarType type;
}
