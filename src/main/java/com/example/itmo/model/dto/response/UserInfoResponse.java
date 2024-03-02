package com.example.itmo.model.dto.response;

import com.example.itmo.model.dto.request.UserInfoRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoResponse extends UserInfoRequest {
    Long id;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj.getClass() != this.getClass()) return false;

        if (this.getId().equals(((UserInfoResponse) obj).getId())) {
            return true;
        }
        else {
            return false;
        }
    }
}
