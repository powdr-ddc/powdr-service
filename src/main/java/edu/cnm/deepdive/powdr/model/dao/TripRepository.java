package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.Trip;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, UUID> {

  Optional<Trip> findFirstByDistance(float distance);
}
