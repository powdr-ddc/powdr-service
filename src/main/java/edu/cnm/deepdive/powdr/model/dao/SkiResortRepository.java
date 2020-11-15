package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.SkiResort;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkiResortRepository  extends JpaRepository<SkiResort, UUID> {

  Optional<SkiResort> findFirstByNameIgnoreCase(String name);
}
