package edu.usal.suravicIntegrity.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByIsEnabled(boolean isEnabled);
    Optional<Category> findByName(String name);
}
