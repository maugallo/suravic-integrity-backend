package edu.usal.suravicIntegrity.provider;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    List<Provider> findByIsEnabled(Boolean isEnabled);
    Optional<Provider> findByCompanyName(String companyName);
}
