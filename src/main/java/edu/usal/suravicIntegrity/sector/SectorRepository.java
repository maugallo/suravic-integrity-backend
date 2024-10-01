package edu.usal.suravicIntegrity.sector;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    List<Sector> findByIsEnabled(boolean isEnabled);
    Optional<Sector> findByName(String name);
}
