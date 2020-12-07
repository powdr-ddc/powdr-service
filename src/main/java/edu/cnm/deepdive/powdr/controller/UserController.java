package edu.cnm.deepdive.powdr.controller;

import edu.cnm.deepdive.powdr.model.entity.User;
import edu.cnm.deepdive.powdr.service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST Controller class for {@link User} to receive parameter values taken from HTTP requests to
 * their respective specified URL endpoints, and returning serialized JSON object results to HTTP
 * responses.
 */
@RestController
@RequestMapping("/users")
@ExposesResourceFor(User.class)
public class UserController {

  private static final String TITLE_PROPERTY_PATTERN =
      BaseParameterPatterns.UUID_PATH_PARAMETER_PATTERN + "/title";
  private static final String DESCRIPTION_PROPERTY_PATTERN =
      BaseParameterPatterns.UUID_PATH_PARAMETER_PATTERN + "/description";
  private static final String CONTENT_PROPERTY_PATTERN =
      BaseParameterPatterns.UUID_PATH_PARAMETER_PATTERN + "/content";
  private static final String CONTRIBUTOR_PARAM_NAME = "contributor";
  private static final String FRAGMENT_PARAM_NAME = "q";
  private static final String ATTACHMENT_DISPOSITION_FORMAT = "attachment; filename=\"%s\"";
  private static final String NOT_RETRIEVED_MESSAGE = "Unable to retrieve previously uploaded file";
  private static final String NOT_STORED_MESSAGE = "Unable to store uploaded content";
  private static final String NOT_WHITELISTED_MESSAGE = "Upload MIME type not in whitelist";
  private static final String FILE_STORE_FAILURE_MESSAGE = "File store error";


  private final UserService userService;

  /**
   * Constructs an instance of {@link UserService}
   *
   * @param userService An instance of {@link UserService}
   */
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Gets the current user.
   *
   * @param auth Security authentication.
   * @return The current user.
   */
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public User me(Authentication auth) {
    return (User) auth.getPrincipal();
  }

  /**
   * Gets all users, ordered by their name.
   *
   * @return A list of {@link User}
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getAllByOrderByName() {
    return userService.getAllByOrderByName();
  }

//  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//  public List<User> getAllFriends(Authentication auth, User confirmer, User requester) {
//    return userService.getAllFriends()
//  }

  /**
   * Gets a user according to their specified ID in the database.
   *
   * @param userId ID of the {@link User}
   * @param auth   Security Authentication
   * @return A user if one exists.
   */
  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<User> getById(@PathVariable UUID userId, Authentication auth) {
    return userService.getById(userId);
  }

  /**
   * Sets a {@link User} to a User's friends; otherwise, throws a {@link NoSuchElementException} if
   * not found.
   *
   * @param friendshipId ID of User
   * @param friend       True if the User is part of the other User's friends.
   * @param auth         Security authentication
   * @return True if the User is part of the other User's friends.
   */
  @PutMapping(value = "/{friendshipId}/friends",
      produces = {
          MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE},
      consumes = {
          MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
  public boolean setFriend(@PathVariable UUID friendshipId, @RequestBody boolean friend,
      Authentication auth) {
    userService.getById(friendshipId)
        .ifPresentOrElse(
            (user) -> userService
                .setFriend(user, friend, (User) auth.getPrincipal()),
            () -> {
              throw new NoSuchElementException();
            }
        );
    return friend;
  }

  /**
   * Stores uploaded file content as an Image alongside the User who uploaded it.
   *
   * @param file MIME content of single file upload.
   * @param auth Authentication token with {@link User} principal.
   * @return The image path connecting a user to an image.
   */
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public String post(
      @RequestParam MultipartFile file, Authentication auth) {
    try {
      User userImage = userService.store(file, (User) auth.getPrincipal());
      return userImage.getImagePath();
    } catch (IOException e) {
      throw new StorageException(e);
    } catch (HttpMediaTypeNotAcceptableException e) {
      throw new MimeTypeNotAllowedException();
    }
  }

  @GetMapping(value = CONTENT_PROPERTY_PATTERN)
  public ResponseEntity<Resource> getImage(
      @SuppressWarnings("MVCPathVariableInspection") @PathVariable UUID id, Authentication auth) {
    return userService.getById(id)
        .map((image) -> {
          try {
            Resource file = userService.retrieve(image);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.contentLength()))
                .body(file);
          } catch (IOException e) {
            throw new StorageException(e);
          }
        })
        .orElseThrow(ImageNotFoundException::new);
  }

  private String dispositionHeader(String filename) {
    return String.format(ATTACHMENT_DISPOSITION_FORMAT, filename);
  }


}
