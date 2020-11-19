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
   * Retrieves a list of {@link Post} by a specified user.
   * @param user Current user signed into the app.
   * @return Returns a list of Posts.
   */
  List<Post> getAllByUser(User user);

  /**
   * Retrieves a list of {@link Post} via a keyword specified by the user.
   * @param keyword A word specified by the user to match related Posts.
   * @return Returns a list of Posts.
   */
  List<Post> getPostByContentContains(String keyword);

  /**
   * Retrieves a list of {@link Post} by the date at which they were posted.
   * @param timestamp The date that a post was created.
   * @return Returns a list of Posts.
   */
  List<Post> getPostByTimestamp(Date timestamp);
}
