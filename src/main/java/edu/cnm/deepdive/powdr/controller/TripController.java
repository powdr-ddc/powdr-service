package edu.cnm.deepdive.powdr.controller;

import edu.cnm.deepdive.powdr.model.entity.Trip;
import edu.cnm.deepdive.powdr.model.entity.User;
import edu.cnm.deepdive.powdr.service.TripService;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RESTful Controller for the {@link Trip} entity that maps request data into JSON.
 */
@RestController
@RequestMapping("/trips")
@ExposesResourceFor(Trip.class)
public class TripController {

  private final TripService tripService;

  /**
   * A constructor for creating an instance of {@link TripService}.
   * @param tripService A reference to the {@link TripService} class.
   */
  @Autowired
  public TripController(TripService tripService) {
    this.tripService = tripService;
  }

  /**
   * Retrieves a specific {@link Trip} by it's UUID.
   * @param tripId The UUID for a specific trip
   * @param auth The current authenticated {@link User}.
   * @return returns a specific Trip.
   */
  @GetMapping(value = "/{tripId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Trip get(@PathVariable UUID tripId, Authentication auth) {
    return tripService.get(tripId)
        .orElseThrow(NoSuchElementException::new);
  }

  /**
   * Retrieves a List of {@link Trip} and orders the response by the distance travelled.
   * @param auth The current authenticated {@link User}.
   * @return returns a list of Trip.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Trip> getAllByOrderByDistance(Authentication auth) {
    return tripService.getAllByOrderByDistance();
  }

  /**
   * Retrieves a specific {@link Trip} and filters the response by it's End timestamp.
   * @param end The End timestamp
   * @param auth The current authenticated {@link User}.
   * @return returns a specific Trip
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<Trip> getByEndTime(@RequestBody Date end, Authentication auth) {
    return tripService.getByEndTime(end);
  }

  /**
   * Retrieves a List of {@link Trip} and orders the response by the Duration.
   * @param start Start timestamp of trip
   * @param end End timestamp of trip
   * @param auth returns the current authenticated {@link User}.
   * @return returns a list of Trip
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Trip> getByDuration(@RequestBody Date start, @RequestBody Date end,
      Authentication auth) {
    return tripService.getByDuration(start, end);
  }

  /**
   * Retrieves or Creates a {@link Trip} depending on whether or not it already exists.
   * @param tripId UUID of Trip
   * @param auth the current authenticated {@link User}.
   * @return returns a Trip.
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Trip post(@RequestBody UUID tripId, Authentication auth) {
    return tripService.getOrCreate(tripId, (User) auth.getPrincipal());
  }

  /**
   * Deletes a specific {@link Trip} by it's UUID.
   * @param tripId UUID of Trip
   * @param auth The current authenticated {@link User}.
   */
  @DeleteMapping(value = "/{tripId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID tripId, Authentication auth) {
    tripService.get(tripId)
        .ifPresent(tripService::delete);
  }

}
