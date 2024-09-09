package edu.usal.suravicIntegrity.percentages;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPercentagesDTO {

    @NotBlank
    @Min(value = 0)
    @Max(value = 9999999)
    private Double vatPercentage;

    @NotBlank
    @Min(value = 0)
    @Max(value = 9999999)
    private Double profitPercentage;

}
