package edu.usal.suravicIntegrity.percentages;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PercentagesMapper {

    PercentagesMapper INSTANCE = Mappers.getMapper(PercentagesMapper.class);

    default PercentagesResponseDTO toDTO(Percentages percentages) {
        PercentagesResponseDTO dto = new PercentagesResponseDTO();
        dto.setId(percentages.getId());
        dto.setVatPercentage(percentages.getVatPercentage());
        dto.setProfitPercentage(percentages.getProfitPercentage());

        return dto;
    }

    default Percentages toEntity(PercentagesRequestDTO dto) {
        Percentages percentages = new Percentages();
        percentages.setVatPercentage(dto.getVatPercentage());
        percentages.setProfitPercentage(dto.getProfitPercentage());

        return percentages;
    }

}
