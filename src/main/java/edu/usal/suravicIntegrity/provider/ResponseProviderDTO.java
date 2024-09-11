package edu.usal.suravicIntegrity.provider;

import edu.usal.suravicIntegrity.contact.Contact;
import edu.usal.suravicIntegrity.contact.ResponseContactDTO;
import edu.usal.suravicIntegrity.percentages.Percentages;
import edu.usal.suravicIntegrity.percentages.ResponsePercentagesDTO;
import edu.usal.suravicIntegrity.sector.ResponseSectorDTO;
import edu.usal.suravicIntegrity.sector.Sector;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseProviderDTO {

    private Long id;

    private ResponseSectorDTO sector;

    private ResponseContactDTO contact;

    private ResponsePercentagesDTO percentages;

    private String vatCondition;

    private String companyName;

    private String firstName;

    private String lastName;

    private String cuit;

    private Boolean isEnabled;

}
