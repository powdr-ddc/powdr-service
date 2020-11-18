package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.Trip;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The {@link TripRepository} extends the {@link JpaRepository}, which provides the
 * CRUD (create, read, update, delete) functions, as well as providing the ability to delete records
 * in batches.
 */
public interface TripRepository extends JpaRepository<Trip, UUID> {

  Optional<Trip> findFirstByDistance(float distance);
}
