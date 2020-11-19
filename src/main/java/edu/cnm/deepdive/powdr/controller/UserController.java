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
 * Repositories are basic update insert delete. Controller is the business logic for the repository.
 */
@RestController
@RequestMapping("/users")
@ExposesResourceFor(User.class)
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public User me(Authentication auth) {
    return (User) auth.getPrincipal();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getAllByOrderByName() {
    return userService.getAllByOrderByName();
  }

  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<User> getById(@PathVariable UUID userId, Authentication auth) {
    return userService.getById(userId);
  }

}
