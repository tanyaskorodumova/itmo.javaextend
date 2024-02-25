package com.example.itmo.model.dto.request;

import com.example.itmo.model.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoRequest {

    @NotEmpty(message = "email must be set")
    String email;

    @NotEmpty(message = "password must be set")
    @Size(min = 6, max = 20, message = "Пароль должен содержать от 6 до 20 символов")
    String password;

    @Pattern(regexp = "^\\D*$", message = "Имя не должно содержать цифр")
    String firstName;
    @Pattern(regexp = "^\\D*$", message = "Фамилия не должна содержать цифр")
    String lastName;
    @Pattern(regexp = "^\\D*$", message = "Отчество не должно содержать цифр")
    String middleName;

    @Min(value = 18, message = "Возраст должен быть не меньше 18 лет")
    Integer age;
    Gender gender;

}
