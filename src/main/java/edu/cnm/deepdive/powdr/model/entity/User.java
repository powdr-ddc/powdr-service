package edu.cnm.deepdive.powdr.model.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class User {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid")
  @Column(nullable = false, updatable = false,
      columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID userId;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true)
  @JsonIgnore
  private String oauthKey;

  private String bio;

  private String imagePath;

  @NonNull
  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("timestamp DESC")
  private final List<Post> posts = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<FavoriteSkiResort> favoriteSkiResorts = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "user")
  private final List<Trip> trips = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Message> messageSender = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Message> messageReceiver = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "confirmer", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Friendship> friendConfirmer = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Friendship> friendRequster = new LinkedList<>();

  public List<FavoriteSkiResort> getFavoriteSkiResort() {
    return favoriteSkiResorts;
  }

  public UUID getUserId() {
    return userId;
  }

  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public List<Post> getPosts() {
    return posts;
  }

  @NonNull
  public List<Trip> getTrips() {
    return trips;
  }

  @NonNull
  public List<Message> getMessageSender() {
    return messageSender;
  }

  @NonNull
  public List<Message> getMessageReceiver() {
    return messageReceiver;
  }

  @NonNull
  public List<Friendship> getFriendConfirmer() {
    return friendConfirmer;
  }

  @NonNull
  public List<Friendship> getFriendRequster() {
    return friendRequster;
  }
}
