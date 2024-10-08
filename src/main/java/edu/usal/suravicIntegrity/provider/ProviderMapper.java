package edu.usal.suravicIntegrity.provider;

import edu.usal.suravicIntegrity.contact.ContactMapper;
import edu.usal.suravicIntegrity.percentages.PercentagesMapper;
import edu.usal.suravicIntegrity.sector.SectorMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProviderMapper {

    ProviderMapper INSTANCE = Mappers.getMapper(ProviderMapper.class);

    default ProviderResponseDTO toDTO(Provider provider) {
        ProviderResponseDTO dto = new ProviderResponseDTO();
        dto.setId(provider.getId());

        dto.setSector(SectorMapper.INSTANCE.toDTO(provider.getSector()));
        dto.setContact(ContactMapper.INSTANCE.toDTO(provider.getContact()));
        dto.setPercentages(PercentagesMapper.INSTANCE.toDTO(provider.getPercentages()));

        dto.setVatCondition(provider.getVatCondition());
        dto.setCompanyName(provider.getCompanyName());
        dto.setFirstName(provider.getFirstName());
        dto.setLastName(provider.getLastName());
        dto.setCuit(provider.getCuit());
        dto.setIsEnabled(provider.getIsEnabled());

        return dto;
    }

    default Provider toEntity(ProviderRequestDTO dto) {
        Provider provider = new Provider();
        provider.setVatCondition(dto.getVatCondition());
        provider.setCompanyName(dto.getCompanyName());
        provider.setFirstName(dto.getFirstName());
        provider.setLastName(dto.getLastName());
        provider.setCuit(dto.getCuit());

        return provider;
    }

}
