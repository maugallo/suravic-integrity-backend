package edu.usal.suravicIntegrity.sector;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SectorMapper {

    SectorMapper INSTANCE = Mappers.getMapper(SectorMapper.class);

    default SectorResponseDTO toDTO(Sector sector) {
        SectorResponseDTO dto = new SectorResponseDTO();
        dto.setId(sector.getId());
        dto.setName(sector.getName());
        dto.setIsEnabled(sector.getIsEnabled());

        return dto;
    }

    default Sector toEntity(SectorRequestDTO dto) {
        Sector sector = new Sector();
        sector.setName(dto.getName());

        return sector;
    }

}
