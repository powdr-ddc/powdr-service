package edu.cnm.deepdive.powdr.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

/**
 * User entity in the database to store information in regards to various users such as:
 * User ID, OAuthKey, their name, a biography section, and an image path for the user pictures.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "user_profile")
public class User {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(nullable = false, updatable = false,
      columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID userId;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true)
  @JsonIgnore
  private String oauthKey;

  private String bio;

  private String imagePath;

  private String contentType;

  @NonNull
  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("timestamp DESC")
  @JsonIgnore
  private final List<Post> posts = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private final List<FavoriteSkiResort> favoriteSkiResorts = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private final List<Trip> trips = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private final List<Message> messageSender = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private final List<Message> messageReceiver = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "confirmer", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private final List<Friendship> friendConfirmer = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private final List<Friendship> friendRequester = new LinkedList<>();

  /**
   * Gets a list of {@link FavoriteSkiResort}.
   * @return Returns a list of FavoriteSkiResorts.
   */
  public List<FavoriteSkiResort> getFavoriteSkiResorts() {
    return favoriteSkiResorts;
  }

  /**
   * Gets the UUID of a {@link User}.
   * @return Returns the UUID of a User
   */
  public UUID getUserId() {
    return userId;
  }

  /**
   * Gets the Oauth Key of a {@link User}.
   * @return Returns a String of OauthKey
   */
  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  /**
   * Sets the OauthKey of a {@link User}.
   * @param oauthKey The oauthKey for a User.
   */
  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }

  /**
   * Gets the bio of a {@link User}.
   * @return Returns a string of the bio of the user.
   */
  public String getBio() {
    return bio;
  }

  /**
   * Sets the bio of a {@link User}.
   * @param bio Bio of a User.
   */
  public void setBio(String bio) {
    this.bio = bio;
  }

  /**
   * Gets the name of a {@link User}
   * @return Returns the name of a User.
   */
  @NonNull
  public String getName() {
    return name;
  }

  /**
   * Sets the name of a {@link User}.
   * @param name Name of a User.
   */
  public void setName(@NonNull String name) {
    this.name = name;
  }

  /**
   * Gets the pathway to an image for a {@link User}.
   * @return Returns a string of the image path.
   */
  public String getImagePath() {
    return imagePath;
  }

  /**
   * Sets the pathway to an image for a {@link User}.
   * @param imagePath Path of an image for a user.
   */
  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  /**
   * Returns the content type of an image.
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * Sets the content type an image.
   * @param contentType Content type for image.
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  /**
   * Gets the posts of a {@link User}.
   * @return Returns the users posts.
   */
  public List<Post> getPosts() {
    return posts;
  }

  /**
   * Gets the trips of a {@link User}
   * @return Returns the users trips.
   */
  @NonNull
  public List<Trip> getTrips() {
    return trips;
  }

  /**
   * Gets a list of {@link User} who sent a message.
   * @return Returns a list of Users who sent messages.
   */
  @NonNull
  public List<Message> getMessageSender() {
    return messageSender;
  }

  /**
   * Gets a list of {@link User} who received a message.
   * @return Returns a list of Users who received a message.
   */
  @NonNull
  public List<Message> getMessageReceiver() {
    return messageReceiver;
  }

  /**
   * Gets a list of confirmers of a {@link User}.
   * @return Returns a list of confirmers.
   */
  @NonNull
  public List<Friendship> getFriendConfirmer() {
    return friendConfirmer;
  }

  /**
   * Gets a list of friend requests for a {@link User}.
   * @return Returns a list of friend requests.
   */
  @NonNull
  public List<Friendship> getFriendRequester() {
    return friendRequester;
  }
}
