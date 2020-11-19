package edu.cnm.deepdive.powdr.controller;

import edu.cnm.deepdive.powdr.model.entity.Post;
import edu.cnm.deepdive.powdr.model.entity.User;
import edu.cnm.deepdive.powdr.service.PostService;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for {@link Post} that helps the service class talk to JSON.
 */
@RestController
@RequestMapping("/posts")
@ExposesResourceFor(Post.class)
public class PostController {

  private final PostService postService;

  /**
   * Constructor for creating an instance of {@link PostService}
   * @param postService Reference to {@link PostService}
   */
  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  /**
   * Gets and displays all posts by a specified user.
   * @param auth The current authenticated user.
   * @return Returns a users list of posts.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Post> getAll(Authentication auth) {
    return postService.getAllByUser((User) auth.getPrincipal());
  }

  /**
   * Gets and displays posts gathered by a keyword.
   * @param keyword A word specified by the user to match related post.
   * @return Returns a list of posts related to a keyword.
   */
  @GetMapping(value = "/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Post> getPostByContentContains(@PathVariable String keyword) {
    return postService.getPostByContentContains(keyword);
  }

  /**
   * Gets and displays posts by date posted.
   * @param timestamp The point in time that a post was created.
   * @return Returns a list of posts created on a certain date.
   */
  @GetMapping
  public List<Post> getPostByTimestamp(Date timestamp) {
    return postService.getPostByTimestamp(timestamp);
  }

  /**
   * Gets or creates posts depending on whether or not they exist.
   * @param postId ID of a specific user.
   * @param auth Current authenticated user.
   * @param imagePath Pathway to the image location.
   * @param content Content within a post.
   * @return Returns a {@link Post} object.
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{postId}")
  @ResponseStatus(HttpStatus.CREATED)
  public Post post(@PathVariable UUID postId, Authentication auth, @RequestBody String imagePath,
      @RequestBody String content) {
    return postService.getOrCreate(postId, (User) auth.getPrincipal(), imagePath, content);
  }

  /**
   * Deletes posts specified by the user.
   * @param postId ID of a specific post.
   * @param auth Current authenticated user.
   */
  @DeleteMapping(value = "/{postId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID postId, Authentication auth) {
    postService.get(postId)
        .ifPresent(postService::delete);
  }

}
