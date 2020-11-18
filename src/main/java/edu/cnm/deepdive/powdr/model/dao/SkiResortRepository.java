package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.SkiResort;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The {@link SkiResortRepository} extends the {@link JpaRepository}, which provides the
 * CRUD (create, read, update, delete) functions, as well as providing the ability to delete records
 * in batches.
 */
public interface SkiResortRepository  extends JpaRepository<SkiResort, UUID> {

  Optional<SkiResort> findFirstByNameIgnoreCase(String name);
}
