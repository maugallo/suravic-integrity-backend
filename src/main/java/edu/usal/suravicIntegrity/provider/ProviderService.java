package edu.usal.suravicIntegrity.provider;

import edu.usal.suravicIntegrity.contact.Contact;
import edu.usal.suravicIntegrity.contact.ContactService;
import edu.usal.suravicIntegrity.exceptions.ResourceNotFoundException;
import edu.usal.suravicIntegrity.percentages.Percentages;
import edu.usal.suravicIntegrity.percentages.PercentagesService;
import edu.usal.suravicIntegrity.sector.Sector;
import edu.usal.suravicIntegrity.sector.SectorService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderService {

    private final ContactService contactService;
    private final PercentagesService percentagesService;
    private final SectorService sectorService;
    private final ProviderRepository providerRepository;
    private final ProviderMapper providerMapper = ProviderMapper.INSTANCE;

    public ProviderService(ContactService contactService, PercentagesService percentagesService, SectorService sectorService, ProviderRepository providerRepository) {
        this.contactService = contactService;
        this.percentagesService = percentagesService;
        this.sectorService = sectorService;
        this.providerRepository = providerRepository;
    }

    // GET METHODS:
    public List<ProviderResponseDTO> findProviders(Boolean isEnabled) {
        List<Provider> providers = providerRepository.findByIsEnabled(isEnabled);

        return providers.stream()
                .map(providerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Provider findProviderById(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el proveedor solicitado con id " + id));
    }

    public ProviderResponseDTO findProviderResponseById(Long id) {
        Provider provider = this.findProviderById(id);

        return providerMapper.toDTO((provider));
    }

    // CREATE METHOD:
    @Transactional
    public String addProvider(ProviderRequestDTO providerRequestDTO) {
        Provider provider = providerMapper.toEntity(providerRequestDTO);

        provider.setIsEnabled(true);
        provider.setSector(sectorService.findSectorById(providerRequestDTO.getSectorId()));
        provider.setContact(contactService.addContact(providerRequestDTO.getContact()));
        provider.setPercentages(percentagesService.addPercentages(providerRequestDTO.getPercentages()));
        providerRepository.save(provider);

        return "Proveedor agregado correctamente";
    }

    // PUT METHOD:
    @Transactional
    public String updateProvider(Long id, ProviderRequestDTO providerRequestDTO) {
        Provider provider = this.findProviderById(id);

        Sector updatedSector = sectorService.findSectorById(providerRequestDTO.getSectorId());
        Contact updatedContact = contactService.updateContact(provider.getContact().getId(), providerRequestDTO.getContact());
        Percentages updatedPercentages = percentagesService.updatePercentages(provider.getPercentages().getId(), providerRequestDTO.getPercentages());

        provider.setSector(updatedSector);
        provider.setContact(updatedContact);
        provider.setPercentages(updatedPercentages);
        provider.setVatCondition(providerRequestDTO.getVatCondition());
        provider.setCompanyName(providerRequestDTO.getCompanyName());
        provider.setFirstName(providerRequestDTO.getFirstName());
        provider.setLastName(providerRequestDTO.getLastName());
        provider.setCuit(providerRequestDTO.getCuit());
        providerRepository.save(provider);

        return "Proveedor actualizado correctamente";
    }

    // DELETE/RECOVER METHOD:
    public String toggleIsEnabled(Long id) {
        Provider provider = this.findProviderById(id);

        provider.setIsEnabled(!provider.getIsEnabled());
        providerRepository.save(provider);

        return (provider.getIsEnabled()) ? "Proveedor recuperado correctamente" : "Proveedor eliminado correctamente";
    }

}
