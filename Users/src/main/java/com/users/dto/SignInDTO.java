package com.users.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignInDTO {

    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;

}
