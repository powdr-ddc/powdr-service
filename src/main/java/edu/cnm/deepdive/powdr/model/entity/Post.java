package edu.cnm.deepdive.powdr.model.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

/**
 * Post entity in the database to store information regarding the posts users can make to the
 * community wall. The posts will have information in regards to the creation date for the content/
 * pictures a user may post.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "timestamp")
    }
)
public class Post {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(nullable = false, updatable = false,
      columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID postId;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @NonNull
  @Column(nullable = false)
  private String content;

  private String imagePath;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;

  /**
   * Retrieves the UUID of a specific {@link Post}.
   * @return UUID of a Post.
   */
  public UUID getPostId() {
    return postId;
  }

  /**
   *
   * @return
   */
  @NonNull
  public User getUser() {
    return user;
  }

  /**
   *
   * @param user
   */
  public void setUser(@NonNull User user) {
    this.user = user;
  }

  /**
   * Retrieves the contents of a specific {@link Post}.
   * @return returns the content of a Post.
   */
  @NonNull
  public String getContent() {
    return content;
  }

  /**
   * Sets the content of a specific {@link Post}.
   * @param content returns the content of a Post.
   */
  public void setContent(@NonNull String content) {
    this.content = content;
  }

  /**
   * Retrieves the timestamp at which a {@link Post} was posted.
   * @return A timestamp for a Post.
   */
  @NonNull
  public Date getTimestamp() {
    return timestamp;
  }

  /**
   * Retrieves the pathway to an Image contained in a {@link Post}.
   * @return returns an Image Pathway
   */
  public String getImagePath() {
    return imagePath;
  }

  /**
   * Establishes a pathway to an Image via a {@link Post}.
   * @param imagePath retuns an Image Pathway
   */
  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }
}
