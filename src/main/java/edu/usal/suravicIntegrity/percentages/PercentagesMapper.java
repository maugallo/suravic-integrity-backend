package edu.usal.suravicIntegrity.percentages;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PercentagesMapper {

    PercentagesMapper INSTANCE = Mappers.getMapper(PercentagesMapper.class);

    default ResponsePercentagesDTO toDTO(Percentages percentages) {
        ResponsePercentagesDTO dto = new ResponsePercentagesDTO();
        dto.setId(percentages.getId());
        dto.setVatPercentage(percentages.getVatPercentage());
        dto.setProfitPercentage(percentages.getProfitPercentage());

        return dto;
    }

    default Percentages toEntity(RequestPercentagesDTO dto) {
        Percentages percentages = new Percentages();
        percentages.setVatPercentage(dto.getVatPercentage());
        percentages.setProfitPercentage(dto.getProfitPercentage());

        return percentages;
    }

}
