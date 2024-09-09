package edu.usal.suravicIntegrity.contact;

import edu.usal.suravicIntegrity.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper = ContactMapper.INSTANCE;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // CREATE METHOD:
    public Contact addContact(RequestContactDTO requestContactDTO) {
        Contact contact = contactMapper.toEntity(requestContactDTO);
        return contactRepository.save(contact);
    }

    // PUT METHOD:
    public Contact updateContact(Long id, RequestContactDTO requestContactDTO) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el contacto solicitado con id " + id));

        contact.setTelephone(requestContactDTO.getTelephone());
        contact.setEmail(requestContactDTO.getEmail());

        return contactRepository.save(contact);
    }

}
