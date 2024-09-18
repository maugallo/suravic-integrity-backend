package edu.usal.suravicIntegrity.percentages;

import edu.usal.suravicIntegrity.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PercentagesService {

    private final PercentagesRepository percentagesRepository;
    private final PercentagesMapper percentagesMapper = PercentagesMapper.INSTANCE;

    public PercentagesService(PercentagesRepository percentagesRepository) {
        this.percentagesRepository = percentagesRepository;
    }

    // CREATE METHOD:
    public Percentages addPercentages(PercentagesRequestDTO percentagesRequestDTO) {
        Percentages percentages = percentagesMapper.toEntity(percentagesRequestDTO);
        return percentagesRepository.save(percentages);
    }

    // PUT METHOD:
    public Percentages updatePercentages(Long id, PercentagesRequestDTO percentagesRequestDTO) {
        Percentages percentages = percentagesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudieron encontrar los porcentajes solicitados con id " + id));

        percentages.setVatPercentage(percentagesRequestDTO.getVatPercentage());
        percentages.setProfitPercentage(percentagesRequestDTO.getProfitPercentage());

        return percentagesRepository.save(percentages);
    }

}
