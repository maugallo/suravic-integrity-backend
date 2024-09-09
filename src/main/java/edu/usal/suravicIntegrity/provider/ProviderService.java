package edu.usal.suravicIntegrity.provider;

import edu.usal.suravicIntegrity.contact.Contact;
import edu.usal.suravicIntegrity.contact.ContactService;
import edu.usal.suravicIntegrity.exceptions.ResourceNotFoundException;
import edu.usal.suravicIntegrity.percentages.Percentages;
import edu.usal.suravicIntegrity.percentages.PercentagesService;
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
    public List<ResponseProviderDTO> findProviders(Boolean isEnabled) {
        List<Provider> providers = providerRepository.findByIsEnabled(isEnabled);

        return providers.stream()
                .map(providerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ResponseProviderDTO findProviderById(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el proveedor solicitado con id " + id));

        return providerMapper.toDTO((provider));
    }

    // CREATE METHOD:
    @Transactional
    public String addProvider(RequestProviderDTO requestProviderDTO) {
        Provider provider = providerMapper.toEntity(requestProviderDTO);
        provider.setContact(contactService.addContact(requestProviderDTO.getContact()));
        provider.setPercentages(percentagesService.addPercentages(requestProviderDTO.getPercentages()));

        providerRepository.save(provider);

        return "Proveedor agregado correctamente";
    }

    // PUT METHOD:
    @Transactional
    public String updateProvider(Long id, RequestProviderDTO requestProviderDTO) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el proveedor solicitado con id " + id));

        Contact updateContact = contactService.updateContact(provider.getContact().getId(), requestProviderDTO.getContact());
        Percentages updatePercentages = percentagesService.updatePercentages(provider.getPercentages().getId(), requestProviderDTO.getPercentages());

        provider.setSector(requestProviderDTO.getSector());
        provider.setContact(updateContact);
        provider.setPercentages(updatePercentages);
        provider.setVatCondition(requestProviderDTO.getVatCondition());
        provider.setCompanyName(requestProviderDTO.getCompanyName());
        provider.setFirstName(requestProviderDTO.getFirstName());
        provider.setLastName(requestProviderDTO.getLastName());
        provider.setCuit(requestProviderDTO.getCuit());
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
