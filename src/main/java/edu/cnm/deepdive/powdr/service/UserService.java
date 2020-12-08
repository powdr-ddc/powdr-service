package edu.cnm.deepdive.powdr.service;

import edu.cnm.deepdive.powdr.controller.UserController;
import edu.cnm.deepdive.powdr.model.dao.UserRepository;
import edu.cnm.deepdive.powdr.model.entity.Friendship;
import edu.cnm.deepdive.powdr.model.entity.User;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

/**
 * User service class to be used and injected in the {@link UserController}.
 */
@Service
public class UserService implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final UserRepository userRepository;
  private final StorageService storageService;

  /**
   * Constructs an instance of {@link UserService}.
   *
   * @param userRepository Takes an instance of {@link UserRepository}
   */
  @Autowired
  public UserService(UserRepository userRepository, StorageService storageService) {
    this.userRepository = userRepository;
    this.storageService = storageService;
  }

  /**
   * Searches the database to get a specific user, and if none are found, a user is created and
   * saved to the database.
   *
   * @param oauthKey OAuthKey of the user
   * @param username Username of the user
   * @return A user through their OAuthKey, or saves a user if the user is not already in the
   * database.
   */
  public User getOrCreate(String oauthKey, String username) {
    return userRepository.findFirstByOauthKey(oauthKey)
        .orElseGet(() -> {
          User user = new User();
          user.setOauthKey(oauthKey);
          user.setName(username);
          return userRepository.save(user);
        }); //takes no parameters but returns something
  }


  /**
   * Gets all users ordered by their name.
   *
   * @return A list of {@link User}
   */
  public List<User> getAllByOrderByName() {
    return userRepository.getAllByOrderByName();
  }

  public List<User> getAllFriends(User confirmer, User requester) {
    return userRepository.getByFriendConfirmerAndFriendRequester(confirmer, requester);
  }

  /**
   * Get a {@link User} according to the ID in the database.
   *
   * @param id ID of the user.
   * @return A user if one exists.
   */
  public Optional<User> getById(UUID id) {
    return userRepository.findById(id);
  }

  /**
   * Checks a {@link User} to see if that User is already in another {@link User}'s friends list. If
   * they are, they are removed from the friends list, if they are not, add that User to the list.
   *
   * @param requester {@link User} who requests the friendship
   * @param friend    boolean that returns True if User is already a friend.
   * @param confirmer {@link User} who receives the request for friendship.
   */
  public void setFriend(User requester, boolean friend, User confirmer) {
    boolean accepted = false;
    List<Friendship> friendships = confirmer.getFriendConfirmer();
    for (
        Iterator<Friendship> iter = friendships.iterator();
        iter.hasNext();
      // Updater in body.
    ) {
      Friendship friendship = iter.next();
      if (friendship.isConfirmed()) {
        accepted = true;
        if (!friend) { // Need to remove it from list of favorites.
          friendship.setConfirmer(null);
          iter.remove();
        }
        break;
      }
    }
    if (friend && !accepted) { // Need to add to list of favorites if not found.
      Friendship friendship = new Friendship();
      friendship.setConfirmer(confirmer);
      friendship.setRequester(requester);
      friendships.add(friendship);
    }
    userRepository.save(confirmer);
  }

  /**
   * Uses the opaque reference contained in {@code image} to return a consumer-usable {@link
   * Resource} to previously uploaded content.
   *
   * @param user {@link User} entity instance referencing the uploaded content.
   * @return {@link Resource} usable in a response body (e.g. for downloading).
   * @throws IOException If the file content cannot&mdash;for any reason&mdash;be read from the file
   *                     store.
   */
  public Resource retrieve(@NonNull User user) throws IOException {
    return storageService.retrieve(user.getImagePath());
  }

  /**
   * Stores the image data to the file store, then constructs and returns the corresponding image
   * path.
   *
   * @param file Uploaded file content.
   * @throws IOException                         If the file content cannot&mdash;for any
   *                                             reason&mdash;be written to the file store.
   * @throws HttpMediaTypeNotAcceptableException If the MIME type of the uploaded file is not on the
   *                                             whitelist.
   */
  public User store(
      @NonNull MultipartFile file, @NonNull User user)
      throws IOException, HttpMediaTypeNotAcceptableException {
    String contentType = file.getContentType();
    String reference = storageService.store(file);
    user.setImagePath(reference);
    user.setContentType((contentType != null) ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE);
    return userRepository.save(user);
  }


  /**
   * Converts a json web token from Google to a {@link UsernamePasswordAuthenticationToken}
   *
   * @param jwt A json web token
   * @return A {@link UsernamePasswordAuthenticationToken}
   */
  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt jwt) { // json web token from google
    Collection<SimpleGrantedAuthority> grants =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    return new UsernamePasswordAuthenticationToken(
        getOrCreate(jwt.getSubject(), jwt.getClaimAsString("name")),
        jwt.getTokenValue(),
        grants
    );
  }
}
