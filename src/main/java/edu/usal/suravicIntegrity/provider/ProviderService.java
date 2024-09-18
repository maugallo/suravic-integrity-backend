package edu.usal.suravicIntegrity.provider;

import edu.usal.suravicIntegrity.contact.Contact;
import edu.usal.suravicIntegrity.contact.ContactService;
import edu.usal.suravicIntegrity.exceptions.ResourceNotFoundException;
import edu.usal.suravicIntegrity.percentages.Percentages;
import edu.usal.suravicIntegrity.percentages.PercentagesService;
import edu.usal.suravicIntegrity.sector.SectorMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderService {

    private final ContactService contactService;
    private final PercentagesService percentagesService;
    private final ProviderRepository providerRepository;
    private final ProviderMapper providerMapper = ProviderMapper.INSTANCE;

    public ProviderService(ContactService contactService, PercentagesService percentagesService, ProviderRepository providerRepository) {
        this.contactService = contactService;
        this.percentagesService = percentagesService;
        this.providerRepository = providerRepository;
    }

    // GET METHODS:
    public List<ProviderResponseDTO> findProviders(Boolean isEnabled) {
        List<Provider> providers = providerRepository.findByIsEnabled(isEnabled);

        return providers.stream()
                .map(providerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProviderResponseDTO findProviderById(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el proveedor solicitado con id " + id));

        return providerMapper.toDTO((provider));
    }

    // CREATE METHOD:
    @Transactional
    public String addProvider(ProviderRequestDTO providerRequestDTO) {
        Provider provider = providerMapper.toEntityFromRequest(providerRequestDTO);
        provider.setIsEnabled(true);
        provider.setContact(contactService.addContact(providerRequestDTO.getContact()));
        provider.setPercentages(percentagesService.addPercentages(providerRequestDTO.getPercentages()));

        providerRepository.save(provider);

        return "Proveedor agregado correctamente";
    }

    // PUT METHOD:
    @Transactional
    public String updateProvider(Long id, ProviderRequestDTO providerRequestDTO) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el proveedor solicitado con id " + id));

        Contact updateContact = contactService.updateContact(provider.getContact().getId(), providerRequestDTO.getContact());
        Percentages updatePercentages = percentagesService.updatePercentages(provider.getPercentages().getId(), providerRequestDTO.getPercentages());

        provider.setSector(SectorMapper.INSTANCE.toEntityFromResponse(providerRequestDTO.getSector()));
        provider.setContact(updateContact);
        provider.setPercentages(updatePercentages);
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
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el proveedor solicitado con id " + id));

        provider.setIsEnabled(!provider.getIsEnabled());
        providerRepository.save(provider);

        return (provider.getIsEnabled()) ? "Proveedor recuperado correctamente" : "Proveedor eliminado correctamente";
    }

}
