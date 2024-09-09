package edu.usal.suravicIntegrity.sector;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SectorMapper {

    SectorMapper INSTANCE = Mappers.getMapper(SectorMapper.class);

    default ResponseSectorDTO toDTO(Sector sector) {
        ResponseSectorDTO dto = new ResponseSectorDTO();
        dto.setId(sector.getId());
        dto.setName(sector.getName());
        dto.setIsEnabled(sector.getIsEnabled());

        return dto;
    }

    default Sector toEntity(RequestSectorDTO dto) {
        Sector sector = new Sector();
        sector.setName(dto.getName());

        return sector;
    }

}
