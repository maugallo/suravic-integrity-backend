package edu.usal.suravicIntegrity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByIsEnabled(Boolean isEnabled);
    Optional<User> findByUsername(String username);

}
