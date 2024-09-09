package edu.usal.suravicIntegrity.sector;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    List<Sector> findByIsEnabled(boolean isEnabled);

}
