package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.SkiResort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The {@link SkiResortRepository} extends the {@link JpaRepository}, which provides the
 * CRUD (create, read, update, delete) functions, as well as providing the ability to delete records
 * in batches.
 */
public interface SkiResortRepository  extends JpaRepository<SkiResort, UUID> {

  /**
   * Retrieves a list of {@link SkiResort} according to it's location via latitude and longitude.
   * @param latitude Latitude of {@link SkiResort} location.
   * @param longitude Longitude of {@link SkiResort} location.
   * @return A list of ski resorts.
   */
  // Might be optional
  SkiResort getByLatitudeAndLongitude(double latitude, double longitude);

  /**
   * Retrieves a ski resort according to its name.
   * @param name Name of {@link SkiResort}
   * @return A ski resort if available.
   */
  Optional<SkiResort> getByName(String name);

  /**
   * Retrieves a list of all ski resorts ordered by their name.
   * @return A list of ski resort.
   */
  List<SkiResort> getAllByOrderByName();

}
