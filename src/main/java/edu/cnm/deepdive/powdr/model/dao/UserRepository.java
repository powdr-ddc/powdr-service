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
   * @param name Name of {@link User}
   * @return A list of users.
   */
  List<User> findByName(String name);
}
