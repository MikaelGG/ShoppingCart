package com.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public ClientDTO(Long id) {
        this.id = id;
    }

}
