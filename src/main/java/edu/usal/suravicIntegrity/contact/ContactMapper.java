package edu.usal.suravicIntegrity.contact;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    default Contact toEntity(RequestContactDTO dto) {
        Contact contact = new Contact();
        contact.setTelephone(dto.getTelephone());
        contact.setEmail(dto.getEmail());

        return contact;
    }

}
