package edu.usal.suravicIntegrity.percentages;

import edu.usal.suravicIntegrity.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PercentagesService {

    private final PercentagesRepository percentagesRepository;
    private final PercentagesMapper percentagesMapper;

    public PercentagesService(PercentagesRepository percentagesRepository, PercentagesMapper percentagesMapper) {
        this.percentagesRepository = percentagesRepository;
        this.percentagesMapper = percentagesMapper;
    }

    // CREATE METHOD:
    public Percentages addPercentages(RequestPercentagesDTO requestPercentagesDTO) {
        Percentages percentages = percentagesMapper.toEntity(requestPercentagesDTO);
        return percentagesRepository.save(percentages);
    }

    // PUT METHOD:
    public Percentages updatePercentages(Long id, RequestPercentagesDTO requestPercentagesDTO) {
        Percentages percentages = percentagesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudieron encontrar los porcentajes solicitados con id " + id));

        percentages.setVatPercentage(requestPercentagesDTO.getVatPercentage());
        percentages.setProfitPercentage(requestPercentagesDTO.getProfitPercentage());

        return percentagesRepository.save(percentages);
    }

}
