package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The {@link UserRepository} extends the {@link JpaRepository}, which provides the
 * CRUD (create, read, update, delete) functions, as well as providing the ability to delete records
 * in batches.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

  /**
   * A specialized query for retrieving a user by the OAuthKey
   * @param oauthKey Takes an OAuthKey
   * @return Either return a {@link User} or return null
   */
  Optional<User> findFirstByOauthKey(String oauthKey);

  /**
   * Retrieves a user by their name.
   * @return A list of users.
   */
  List<User> getAllByOrderByName();

  /**
   * A specialized query for retrieving a list of friends by whether they have sent a request to
   * the current User and the current User has confirmed,
   * or have received a request by the current User and they have confirmed
   * @param confirmer A user who has sent a friend request
   * @param requester a user who has received friend request
   * @return A list of User's you are friends with
   */
  List<User> getByFriendConfirmerAndFriendRequester(User confirmer, User requester);

}
