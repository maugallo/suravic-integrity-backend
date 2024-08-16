package edu.usal.suravicIntegrity.user;

import edu.usal.suravicIntegrity.validation.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserDTO {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 40, message = "El nombre de usuario no puede tener más de 40 caracteres")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min= 6, max = 60, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @EnumValidator(enumClass = UserRole.class)
    private String role;

}
