package edu.cnm.deepdive.powdr.controller;

import edu.cnm.deepdive.powdr.model.entity.SkiResort;
import edu.cnm.deepdive.powdr.model.entity.User;
import edu.cnm.deepdive.powdr.service.SkiResortService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller class for {@link SkiResort} to receive parameter values taken from HTTP requests to
 * their respective specified URL endpoints, and returning serialized JSON object results to HTTP
 * responses.
 */
@RestController
@RequestMapping("/ski-resorts")
@ExposesResourceFor(SkiResort.class)
public class SkiResortController {

  private final SkiResortService skiResortService;

  /**
   * Constructs an instance of {@link SkiResortService}.
   * @param skiResortService An instance of ski resort.
   */
  @Autowired
  public SkiResortController(SkiResortService skiResortService) {
    this.skiResortService = skiResortService;
  }

  /**
   * Gets all ski resorts.
   * @param auth Security authentication.
   * @return A list of {@link SkiResort}.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<SkiResort> getAll(Authentication auth) {
    return skiResortService.getAll();
  }

  /**
   * Gets a {@link SkiResort} according to it's latitude and longitude.
   * @param latitude Latitude of ski resort.
   * @param longitude Longitude of ski resort.
   * @param auth Security authentication.
   * @return A ski resort.
   */
  @GetMapping(value = "/location", produces = MediaType.APPLICATION_JSON_VALUE)
  public SkiResort getByLatitudeAndLongitude(@RequestBody double latitude,
      @RequestBody double longitude, Authentication auth) {
    return skiResortService.getByLatitudeAndLongitude(latitude, longitude);
  }

  /**
   * Gets a list of {@link SkiResort} according to it's name.
   * @param name Name of ski resort.
   * @param auth Security authentication.
   * @return A ski resort if available.
   */
  @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<SkiResort> getByName(@PathVariable String name, Authentication auth) {
    return skiResortService.getByName(name);
  }

  /**
   * Gets a ski resort according to it's ID.
   * @param skiResortId ID of ski resort.
   * @param auth Security authentication.
   * @return A ski resort.
   */
  @GetMapping(value = "/{skiResortId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public SkiResort get(@PathVariable UUID skiResortId, Authentication auth) {
    return skiResortService.get(skiResortId)
        .orElseThrow(NoSuchElementException::new);
  }

  /**
   * Sets a ski resort to a user's favorites; otherwise, throws a {@link NoSuchElementException} if
   * not found.
   * @param skiResortId ID of ski resort
   * @param favorite True if the ski resort is part of the user's favorites.
   * @param auth Security authentication
   * @return True if the ski resort is part of the user's favorites.
   */
  @PutMapping(value = "/{skiResortId}/favorite",
      produces = {
          MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE},
      consumes = {
          MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
  public boolean setFavorite(@PathVariable UUID skiResortId, @RequestBody boolean favorite,
      Authentication auth) {
    skiResortService.get(skiResortId)
        .ifPresentOrElse(
            (skiResort) -> skiResortService
                .setFavorite(skiResort, favorite, (User) auth.getPrincipal()),
            () -> {
              throw new NoSuchElementException();
            }
        );
    return favorite;
  }

  /**
   * Saves a ski resort to the database.
   * @param skiResort Ski resort to be saved.
   * @param auth Security authentication.
   * @return A ski resort saved to the database.
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public SkiResort post(@RequestBody SkiResort skiResort, Authentication auth) {
    return skiResortService.save(skiResort, (User) auth.getPrincipal());
  }

  /**
   * Delete a ski resort from the database according to it's ID if one exists.
   * @param skiResortId ID of specified ski resort.
   * @param auth Security authentication.
   */
  @DeleteMapping(value = "/{skiResortId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID skiResortId, Authentication auth) {
    skiResortService.get(skiResortId)
        .ifPresent(skiResortService::delete);
  }

}
