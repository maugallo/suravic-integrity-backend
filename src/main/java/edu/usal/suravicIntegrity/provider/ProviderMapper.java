package edu.usal.suravicIntegrity.provider;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProviderMapper {

    ProviderMapper INSTANCE = Mappers.getMapper(ProviderMapper.class);

    default ResponseProviderDTO toDTO(Provider provider) {
        ResponseProviderDTO dto = new ResponseProviderDTO();
        dto.setId(provider.getId());
        dto.setSector(provider.getSector());
        dto.setContact(provider.getContact());
        dto.setPercentages(provider.getPercentages());
        dto.setVatCondition(provider.getVatCondition());
        dto.setFirstName(provider.getFirstName());
        dto.setLastName(provider.getLastName());
        dto.setCuit(provider.getCuit());
        dto.setIsEnabled(provider.getIsEnabled());

        return dto;
    }

    default Provider toEntity(RequestProviderDTO dto) {
        Provider provider = new Provider();
        provider.setSector(dto.getSector());
        provider.setVatCondition(dto.getVatCondition());
        provider.setCompanyName(dto.getCompanyName());
        provider.setFirstName(dto.getFirstName());
        provider.setLastName(dto.getLastName());
        provider.setCuit(dto.getCuit());

        return provider;
    }

}
