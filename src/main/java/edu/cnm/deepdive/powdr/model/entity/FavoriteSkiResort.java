package edu.cnm.deepdive.powdr.model.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

/**
 * Favorite Ski Resort joint entity in the database to join the {@link User} entity and {@link SkiResort}
 * entity to allow a user to save a specific ski resort to their favorites for easy access.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    uniqueConstraints =
        @UniqueConstraint(columnNames = {"user_id", "ski_resort_id"})
)
public class FavoriteSkiResort {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(nullable = false, updatable = false,
      columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID id;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "ski_resort_id", nullable = false, updatable = false)
  private SkiResort skiResort;

  /**
   * Retrieves the UUID of a {@link FavoriteSkiResort}.
   * @return UUID of a FavoriteSkiResort.
   */
  public UUID getId() {
    return id;
  }

  /**
   * Retrieves the {@link User} who has the ski resort as a favorite.
   * @return The current {@link User}
   */
  @NonNull
  public User getUser() {
    return user;
  }

  /**
   * Sets the current {@link User} as the owner of the FavoriteSkiResort
   * @param user The current instance of User.
   */
  public void setUser(@NonNull User user) {
    this.user = user;
  }

  /**
   * Retrieves the {@link SkiResort} that will be marked as a favorite.
   * @return The selected SkiResort
   */
  @NonNull
  public SkiResort getSkiResort() {
    return skiResort;
  }

  /**
   * Sets the selected {@link SkiResort} as a favorite of the current User.
   * @param skiResort The selected SkiResort.
   */
  public void setSkiResort(@NonNull SkiResort skiResort) {
    this.skiResort = skiResort;
  }
}
