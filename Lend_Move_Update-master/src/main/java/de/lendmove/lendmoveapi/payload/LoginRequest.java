package de.lendmove.lendmoveapi.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String email;


}