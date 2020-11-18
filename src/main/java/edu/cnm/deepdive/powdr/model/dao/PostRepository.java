package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.Post;
import edu.cnm.deepdive.powdr.model.entity.User;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The {@link PostRepository} extends the {@link JpaRepository}, which provides the
 * CRUD (create, read, update, delete) functions, as well as providing the ability to delete records
 * in batches.
 */
public interface PostRepository extends JpaRepository<Post, UUID> {

  /**
   * Gets all {@link Post} for a specific {@link User}
   * @param user Takes an instance of {@link User}
   * @return A list of posts
   */
  List<Post> getAllByUser(User user);

  List<Post> findPostByContentNotContains(String keyword);

  List<Post> findPostByTimestamp(Date timestamp);
}
