package edu.cnm.deepdive.powdr.service;

import edu.cnm.deepdive.powdr.model.dao.TripRepository;
import edu.cnm.deepdive.powdr.model.entity.Trip;
import edu.cnm.deepdive.powdr.model.entity.User;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A Service class for the {@link Trip} entity that participates in dependency injection,
 * and performs more detailed methods for obtaining information for the
 * {@link edu.cnm.deepdive.powdr.controller.TripController} and {@link TripRepository}.
 */
@Service
public class TripService {

  private final TripRepository tripRepository;

  /**
   * A Constructor that creates an instance of the {@link TripRepository}.
   * @param tripRepository Repository class for {@link Trip}.
   */
  @Autowired
  public TripService(TripRepository tripRepository) {
    this.tripRepository = tripRepository;
  }

  /**
   * A method for retrieving a specific {@link Trip} by it's UUID, or if it doesn't exist, creates
   * a Trip and sets the current {@link User} as the owner.
   * @param id UUID of Trip
   * @param user The user to be designated as the owner.
   * @return A specific Trip.
   */
  public Trip getOrCreate(UUID id, User user) {
    return tripRepository.findById(id)
        .orElseGet(() -> {
          Trip trip = new Trip();
          trip.setUser(user);
          return tripRepository.save(trip);
        });
  }

  /**
   * Retrieves a specific {@link Trip} and filters the response by it's End timestamp.
   * @param end End Timestamp
   * @return A specific Trip
   */
  public Optional<Trip> getByEndTime(Date end) {
    return tripRepository.getByEndTime(end);
  }

  /**
   * Retrieves a List of {@link Trip} filtered by Duration.
   * @param start Start Timestamp
   * @param end End Timestamp
   * @return A List of Trip
   */
  public List<Trip> getByDuration(Date start, Date end) {
    return tripRepository.getAllByStartTimeAndEndTime(start, end);
  }

  /**
   * Retrieves a List of {@link Trip} and orders the response by the Duration.
   * @return A specific Trip.
   */
  public List<Trip> getAllByOrderByDistance() {
    return tripRepository.getAllByOrderByDistance();
  }

  /**
   * Retrieves a specific {@link Trip} by it's UUID.
   * @param id UUID of Trip
   * @return A specific Trip.
   */
  public Optional<Trip> get(UUID id) {
    return tripRepository.findById(id);
  }

  /**
   * Deletes a specific {@link Trip} by it's UUID.
   * @param trip The selected Trip.
   */
  public void delete(Trip trip) {
    tripRepository.delete(trip);
  }

}
