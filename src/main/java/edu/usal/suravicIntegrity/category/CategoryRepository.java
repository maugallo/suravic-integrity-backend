package edu.usal.suravicIntegrity.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByIsEnabled(boolean isEnabled);

}
