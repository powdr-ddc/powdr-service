package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.Trip;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * An extended {@link JpaRepository} for the {@link Trip} entity. Allowing for CRUD functionality
 * and specialized queries.
 */
public interface TripRepository extends JpaRepository<Trip, UUID> {

  /**
   * Retrieves a List of {@link Trip} ordered by Distance.
   * @return List of Trip
   */
  List<Trip> getAllByOrderByDistance();

  /**
   * Retrieves a List of {@link Trip} filtered by duration using the Start and End timestamps.
   * @param start Start Timestamp
   * @param end End Timestamp
   * @return List of Trip.
   */
  List<Trip> getByDuration(Date start, Date end);

  /** Retrieves a specific {@link Trip} for the provided End timestamp
   *
   * @param end End Timestamp
   * @return Either a Trip or a null value.
   */
  Optional<Trip> getByEndTime(Date end);
}
