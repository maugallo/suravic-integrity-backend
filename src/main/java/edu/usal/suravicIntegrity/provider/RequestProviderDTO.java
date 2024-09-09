package edu.usal.suravicIntegrity.provider;

import edu.usal.suravicIntegrity.contact.RequestContactDTO;
import edu.usal.suravicIntegrity.percentages.RequestPercentagesDTO;
import edu.usal.suravicIntegrity.sector.Sector;
import edu.usal.suravicIntegrity.validation.EnumValidator;
import edu.usal.suravicIntegrity.validation.UniqueValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestProviderDTO {

    private Sector sector;

    private RequestContactDTO contact;

    private RequestPercentagesDTO percentages;

    @EnumValidator(enumClass = VatCondition.class)
    private String vatCondition;

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ]+$", message = "La razón social no puede tener caracteres especiales")
    @UniqueValidator(entityClass = Provider.class, fieldName = "companyName", message = "La razón social ingresada ya existe")
    private String companyName;

    @NotBlank
    @Size(max = 40)
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+$", message = "El nombre solo acepta letras")
    private String firstName;

    @NotBlank
    @Size(max = 40)
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+$", message = "El apellido solo acepta letras")
    private String lastName;

    @NotBlank
    @Size(min = 13, max = 13)
    @Pattern(regexp = "^[0-9]{2}-[0-9]{8}-[0-9]$", message = "El cuit no cuenta con el formato adecuado")
    @UniqueValidator(entityClass = Provider.class, fieldName = "cuit", message = "El cuit ingresado ya existe")
    private String cuit;

}
