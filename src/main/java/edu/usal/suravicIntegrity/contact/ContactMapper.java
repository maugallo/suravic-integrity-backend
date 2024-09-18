package edu.usal.suravicIntegrity.contact;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    default ContactResponseDTO toDTO(Contact contact) {
        ContactResponseDTO dto = new ContactResponseDTO();
        dto.setId(contact.getId());
        dto.setEmail(contact.getEmail());
        dto.setTelephone(contact.getTelephone());

        return dto;
    }

    default Contact toEntity(ContactRequestDTO dto) {
        Contact contact = new Contact();
        contact.setTelephone(dto.getTelephone());
        contact.setEmail(dto.getEmail());

        return contact;
    }

}
