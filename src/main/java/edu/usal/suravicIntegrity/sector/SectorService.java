package edu.usal.suravicIntegrity.sector;

import edu.usal.suravicIntegrity.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectorService {

    private final SectorRepository sectorRepository;
    private final SectorMapper sectorMapper = SectorMapper.INSTANCE;

    public SectorService(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    // GET METHODS:
    public List<ResponseSectorDTO> findAll(Boolean isEnabled) {
        List<Sector> sectors = sectorRepository.findByIsEnabled(isEnabled);

        return sectors.stream()
                .map(sectorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ResponseSectorDTO findById(Long id) {
        Sector sector = sectorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el rubro solicitado con id " + id));

        return sectorMapper.toDTO(sector);
    }

    // CREATE METHOD:
    public String addSector(RequestSectorDTO requestSectorDTO) {
        Sector sector = sectorMapper.toEntity(requestSectorDTO);
        sector.setIsEnabled(true);
        sectorRepository.save(sector);

        return "Rubro creado correctamente";
    }

    // PUT METHOD:
    public String updateSector(Long id, RequestSectorDTO requestSectorDTO) {
        Sector sector = sectorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el rubro solicitado con id " + id));

        sector.setName(requestSectorDTO.getName());

        sectorRepository.save(sector);

        return "Rubro actualizado correctamente";
    }

    // DELETE/RECOVER METHOD:
    public String toggleIsEnabled(Long id) {
        Sector sector = sectorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el rubro solicitado con id " + id));

        sector.setIsEnabled(!sector.getIsEnabled());
        sectorRepository.save(sector);

        return (sector.getIsEnabled() ? "Rubro recuperado correctamente" : "Rubro eliminado correctamente");
    }

}
