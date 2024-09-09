package edu.usal.suravicIntegrity.provider;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    List<Provider> findByIsEnabled(Boolean isEnabled);

}
