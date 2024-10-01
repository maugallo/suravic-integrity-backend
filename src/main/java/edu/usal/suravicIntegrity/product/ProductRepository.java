package edu.usal.suravicIntegrity.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsEnabled(Boolean isEnabled);
    Optional<Product> findByTitle(String title);
}
