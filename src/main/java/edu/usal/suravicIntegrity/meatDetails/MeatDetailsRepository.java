package edu.usal.suravicIntegrity.meatDetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeatDetailsRepository extends JpaRepository<MeatDetails, Long> {
    List<MeatDetails> findAllByIdBetween(long id1, long id2);
}
