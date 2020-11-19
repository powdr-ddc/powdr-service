package edu.cnm.deepdive.powdr.service;

import edu.cnm.deepdive.powdr.controller.UserController;
import edu.cnm.deepdive.powdr.model.dao.UserRepository;
import edu.cnm.deepdive.powdr.model.entity.Friendship;
import edu.cnm.deepdive.powdr.model.entity.User;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

/**
 * User service class to be used and injected in the {@link UserController}.
 */
@Service
public class UserService implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final UserRepository userRepository;

  /**
   * Constructs an instance of {@link UserService}.
   * @param userRepository Takes an instance of {@link UserRepository}
   */
  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Searches the database to get a specific user, and if none are found, a user is created and
   * saved to the database.
   *
   * @param oauthKey OAuthKey of the user
   * @param username Username of the user
   * @return A user through their OAuthKey, or saves a user if the user is not already in the database.
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
   * @return A list of {@link User}
   */
  public List<User> getAllByOrderByName() {
    return userRepository.getAllByOrderByName();
  }

  /**
   * Get a {@link User} according to the ID in the database.
   * @param id ID of the user.
   * @return A user if one exists.
   */
  public Optional<User> getById(UUID id) {
    return userRepository.findById(id);
  }

  /**
   * Checks a {@link User} to see if that User is already in another {@link User}'s friends list.
   * If they are, they are removed from the friends list, if they are not, add that User to the list.
   * @param requester {@link User} who requests the friendship
   * @param friend boolean that returns True if User is already a friend.
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
//          friendship.setConfirmer(null);
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
   * Converts a json web token from Google to a {@link UsernamePasswordAuthenticationToken}
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
