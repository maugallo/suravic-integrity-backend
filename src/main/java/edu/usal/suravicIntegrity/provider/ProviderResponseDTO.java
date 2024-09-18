package edu.usal.suravicIntegrity.provider;

import edu.usal.suravicIntegrity.contact.ContactResponseDTO;
import edu.usal.suravicIntegrity.percentages.PercentagesResponseDTO;
import edu.usal.suravicIntegrity.sector.SectorResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderResponseDTO {

    private Long id;

    private SectorResponseDTO sector;

    private ContactResponseDTO contact;

    private PercentagesResponseDTO percentages;

    private String vatCondition;

    private String companyName;

    private String firstName;

    private String lastName;

    private String cuit;

    private Boolean isEnabled;

}
