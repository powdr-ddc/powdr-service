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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller class for {@link Post} to receive parameter values taken from HTTP requests to
 * their respective specified URL endpoints, and returning serialized JSON object results to HTTP
 * responses.
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
   * Gets and displays posts gathered by a keyword.
   * @param keyword A word specified by the user to match related post.
   * @return Returns a list of posts related to a keyword.
   */
  @GetMapping(value = "/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Post> getPostByContentContains(Authentication auth, @PathVariable String keyword) {
    return postService.getPostByContentContains(keyword);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"days"})
  public List<Post> getByDateRange(Authentication auth, @RequestParam int days) {
    return postService.getByMostRecent(days);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"start", "end"})
  public List<Post> getByDateRange(Authentication auth,
      @RequestParam Date start, @RequestParam Date end) {
    return postService.getByDateRange(start, end);
  }

  /**
   * Gets and displays all posts by a specified user.
   * @param auth The current authenticated user.
   * @return Returns a users list of posts.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"!start", "!end"})
  public List<Post> getAll(Authentication auth) {
    return postService.getAllByUser((User) auth.getPrincipal());
  }


  /**
   * Gets and displays posts by date posted.
   * @param timestamp The point in time that a post was created.
   * @return Returns a list of posts created on a certain date.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Post> getPostByTimestamp(@RequestParam Date timestamp) {
    return postService.getPostByTimestamp(timestamp);
  }


  /**
   * Gets or creates posts depending on whether or not they exist.
   * @param post ID of a specific user.
   * @param auth Current authenticated user.
   * @return Returns a {@link Post} object.
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Post post(@RequestBody Post post, Authentication auth) {
    return postService.save(post, (User) auth.getPrincipal());
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)


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
