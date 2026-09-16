package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegisterRequest {

    @NotBlank(message = "El usuario es obligatorio")
    public String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(
            regexp = "^[0-9]{4,8}$",
            message = "La contraseña debe contener entre 4 y 8 dígitos"
    )
    public String password;

    public RegisterRequest() {
    }
}
