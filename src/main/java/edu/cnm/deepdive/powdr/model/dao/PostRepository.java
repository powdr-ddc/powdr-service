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

  /**
   * Gets a list of posts by a keyword specified by the {@link User}
   * @param keyword The keyword used to search the content of a post
   * @return A list of {@link Post}
   */
  List<Post> findPostByContentNotContains(String keyword);

  /**
   * Gets a list of posts according to a specific timestamp
   * @param timestamp Timestamp to be used to search for {@link Post}
   * @return A list of {@link Post}
   */
  List<Post> findPostByTimestamp(Date timestamp);
}
