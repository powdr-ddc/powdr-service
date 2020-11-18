package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.SkiResort;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkiResortRepository  extends JpaRepository<SkiResort, UUID> {

  // Might be optional.
  SkiResort getByLatitudeAndLongitude(double latitude, double longitude);

  List<SkiResort> getByName(String name);

  List<SkiResort> getAllByOrderByName();

}
