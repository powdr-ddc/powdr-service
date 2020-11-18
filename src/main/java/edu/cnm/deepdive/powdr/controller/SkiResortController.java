package edu.cnm.deepdive.powdr.controller;

import edu.cnm.deepdive.powdr.model.entity.SkiResort;
import edu.cnm.deepdive.powdr.model.entity.User;
import edu.cnm.deepdive.powdr.service.SkiResortService;;
import java.util.List;
import java.util.NoSuchElementException;
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

@RestController
@RequestMapping("/ski-resorts")
@ExposesResourceFor(SkiResort.class)
public class SkiResortController {

  private final SkiResortService skiResortService;

  @Autowired
  public SkiResortController(SkiResortService skiResortService) {
    this.skiResortService = skiResortService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<SkiResort> getAll(Authentication auth) {
    return skiResortService.getAll();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public SkiResort getByLatitudeAndLongitude(@RequestBody double latitude,
      @RequestBody double longitude, Authentication auth) {
    return skiResortService.getByLatitudeAndLongitude(latitude, longitude);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<SkiResort> getByName(@RequestBody String name, Authentication auth) {
    return skiResortService.getByName(name);
  }

  @GetMapping(value = "/{skiResortId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public SkiResort get(@PathVariable UUID skiResortId, Authentication auth) {
    return skiResortService.get(skiResortId)
        .orElseThrow(NoSuchElementException::new);
  }


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

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public SkiResort post(@RequestBody SkiResort skiResort, Authentication auth) {
    return skiResortService.save(skiResort, (User) auth.getPrincipal());
  }

  @DeleteMapping(value = "/{skiResortId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID skiResortId, Authentication auth) {
    skiResortService.get(skiResortId)
        .ifPresent(skiResortService::delete);
  }

}
