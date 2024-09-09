package edu.usal.suravicIntegrity.percentages;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PercentagesMapper {

    PercentagesMapper INSTANCE = Mappers.getMapper(PercentagesMapper.class);

    default Percentages toEntity(RequestPercentagesDTO dto) {
        Percentages percentages = new Percentages();
        percentages.setVatPercentage(dto.getVatPercentage());
        percentages.setProfitPercentage(dto.getProfitPercentage());

        return percentages;
    }

}
