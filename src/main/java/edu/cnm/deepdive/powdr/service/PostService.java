package edu.cnm.deepdive.powdr.service;

import edu.cnm.deepdive.powdr.model.dao.PostRepository;
import edu.cnm.deepdive.powdr.model.entity.Post;
import edu.cnm.deepdive.powdr.model.entity.User;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

/**
 * A Service class for the {@link Post} entity that participates in dependency injection,
 * and performs more detailed methods for obtaining information for the
 * {@link edu.cnm.deepdive.powdr.controller.PostController} and {@link PostRepository}.
 */
@Service
public class PostService {

  private final PostRepository postRepository;
  private final StorageService storageService;

  /**
   * Creates an instance of {@link PostRepository}
   * @param postRepository Reference to {@link PostRepository}
   */
  @Autowired
  public PostService(PostRepository postRepository, StorageService storageService) {
    this.postRepository = postRepository;
    this.storageService = storageService;
  }

  /**
   * Saves a {@link Post} to a {@link User}.
   * @param post Post to be saved.
   * @param user User to save the post.
   * @return A saved post.
   */
  public Post save(Post post, User user) {
    if (post.getPostId() == null) {
      post.setUser(user);
    }
    return postRepository.save(post);
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
   * Stores an image for a post in the database.
   * @param file Multipart file of the image
   * @param post An instance of {@link Post}
   * @return A saved image post.
   * @throws IOException
   * @throws HttpMediaTypeNotAcceptableException
   */
  public Post store(
      @NonNull MultipartFile file, @NonNull Post post)
      throws IOException, HttpMediaTypeNotAcceptableException {
    String contentType = file.getContentType();
    String reference = storageService.store(file);
    post.setImagePath(reference);
    post.setContentType((contentType != null) ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE);
    return postRepository.save(post);
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
   * Retrieves all {@link Post} in the specified date range.
   * @param start Start date of search query.
   * @param end End date of search query.
   * @return A list of post according to a date range.
   */
  public List<Post> getByDateRange(Date start, Date end) {
    return postRepository.findAllByTimestampBetweenOrderByTimestampAsc(start, end);
  }

  /**
   * Retrieves a list of {@link Post} made since a the specified date.
   * @param days Number of days to retrieve posts.
   * @return A list of posts.
   */
  public List<Post> getByMostRecent(int days) {
    Date now = new Date();
    Date cutoff = new Date(now.getTime() - days * 24 * 60 * 60 * 1000);
    return postRepository.findAllByTimestampBetweenOrderByTimestampAsc(cutoff, now);
  }


  /**
   * Deletes a specific {@link Post} specified by the user.
   * @param post The selected Post.
   */
  public void delete(Post post) {
    postRepository.delete(post);
  }

}
