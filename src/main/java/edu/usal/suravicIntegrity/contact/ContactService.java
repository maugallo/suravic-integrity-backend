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
    public Contact addContact(ContactRequestDTO contactRequestDTO) {
        Contact contact = contactMapper.toEntity(contactRequestDTO);
        return contactRepository.save(contact);
    }

    // PUT METHOD:
    public Contact updateContact(Long id, ContactRequestDTO contactRequestDTO) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el contacto solicitado con id " + id));

        contact.setTelephone(contactRequestDTO.getTelephone());
        contact.setEmail(contactRequestDTO.getEmail());

        return contactRepository.save(contact);
    }

}
