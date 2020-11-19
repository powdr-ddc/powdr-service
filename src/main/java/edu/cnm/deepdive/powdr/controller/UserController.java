package edu.cnm.deepdive.powdr.controller;

import edu.cnm.deepdive.powdr.model.entity.FavoriteSkiResort;
import edu.cnm.deepdive.powdr.model.entity.SkiResort;
import edu.cnm.deepdive.powdr.model.entity.User;
import edu.cnm.deepdive.powdr.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller class for {@link User} to receive parameter values taken from HTTP requests to
 * their respective specified URL endpoints, and returning serialized JSON object results to HTTP
 * responses.
 */
@RestController
@RequestMapping("/users")
@ExposesResourceFor(User.class)
public class UserController {

  private final UserService userService;

  /**
   * Constructs an instance of {@link UserService}
   * @param userService An instance of {@link UserService}
   */
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Gets the current user.
   * @param auth Security authentication.
   * @return The current user.
   */
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public User me(Authentication auth) {
    return (User) auth.getPrincipal();
  }

  /**
   * Gets all users, ordered by their name.
   * @return A list of {@link User}
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getAllByOrderByName() {
    return userService.getAllByOrderByName();
  }

  /**
   * Gets a user according to their specified ID in the database.
   * @param userId ID of the {@link User}
   * @param auth Security Authentication
   * @return A user if one exists.
   */
  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<User> getById(@PathVariable UUID userId, Authentication auth) {
    return userService.getById(userId);
  }

}
