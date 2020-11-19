package edu.cnm.deepdive.powdr.service;

import edu.cnm.deepdive.powdr.model.dao.PostRepository;
import edu.cnm.deepdive.powdr.model.entity.Post;
import edu.cnm.deepdive.powdr.model.entity.User;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A Service class for the {@link Post} entity that participates in dependency injection,
 * and performs more detailed methods for obtaining information for the
 * {@link edu.cnm.deepdive.powdr.controller.PostController} and {@link PostRepository}.
 */
@Service
public class PostService {

  private final PostRepository postRepository;

  /**
   * Creates an instance of {@link PostRepository}
   * @param postRepository Reference to {@link PostRepository}
   */
  @Autowired
  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  /**
   * Gets or creates posts depending on whether or not they exist.
   * @param id ID of a specific user.
   * @param user Current user signed into the app.
   * @param imagePath Pathway to the image location.
   * @param content Content within a post.
   * @return Returns a {@link Post} object.
   */
  public Post getOrCreate(UUID id, User user, String imagePath, String content) {
    return postRepository.findById(id)
        .orElseGet(() -> {
          Post post = new Post();
          post.setUser(user);
          post.setImagePath(imagePath);
          post.setContent(content);
          return postRepository.save(post);
        });
  }

  /**
   * Retrieves a specific {@link Post} by the UUID.
   * @param id ID of a specific user.
   * @return Returns a Post.
   */
  public Optional<Post> get(UUID id) {
    return postRepository.findById(id);
  }

  /**
   * Retrieves all {@link Post} created by a specified user.
   * @param user Current user signed into the app.
   * @return Returns a list of Posts.
   */
  public List<Post> getAllByUser(User user) {
    return postRepository.getAllByUser(user);
  }

  /**
   * Retrieves all {@link Post} related to the keyword input by the user.
   * @param keyword A word specified by the user to match related post.
   * @return Returns a list of Posts.
   */
  public List<Post> getPostByContentContains(String keyword) {
    return postRepository.getPostByContentContains(keyword);
  }

  /**
   * Retrieves all {@link Post} by the date they were posted.
   * @param timestamp The date that a post was created.
   * @return Returns a list of Posts.
   */
  public List<Post> getPostByTimestamp(Date timestamp) {
    return postRepository.getPostByTimestamp(timestamp);
  }

  /**
   * Deletes a specific {@link Post} specified by the user.
   * @param post The selected Post.
   */
  public void delete(Post post) {
    postRepository.delete(post);
  }

}