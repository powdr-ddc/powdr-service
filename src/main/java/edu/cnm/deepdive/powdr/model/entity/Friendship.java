package edu.cnm.deepdive.powdr.model.entity;

import com.sun.istack.Nullable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

/**
 * Friendship entity in the database to store information that allows users to friend each other.
 * There is the requester--the person who sent the friend request--and the confirmer, who must
 * confirm the requesters friend request.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Friendship {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(nullable = false, updatable = false,
      columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID friendshipId;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "requester_id", nullable = false, updatable = false)
  private User requester;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "confirmer_id", nullable = false, updatable = false)
  private User confirmer;

  private boolean confirmed;

  /**
   * Retrieves the UUID of a specific {@link Friendship}
   * @return UUID of Friendship.
   */
  public UUID getFriendshipId() {
    return friendshipId;
  }

  /** Retrieves the {@link User} that sent the friend request.
   * @return The User that sent the request.
   */
  @NonNull
  public User getRequester() {
    return requester;
  }

  /**
   * Sets the current {@link User} as the sender of the friend request
   * @param requester The current User sending the request.
   */
  public void setRequester(@NonNull User requester) {
    this.requester = requester;
  }

  /** Retrieves the {@link User} that received the friend request.
   * @return The User that received the request.
   */
  @NonNull
  public User getConfirmer() {
    return confirmer;
  }

  /**
   * Sets the current {@link User} as the receiver of the friend request
   * @param confirmer The current User receiving the request.
   */
  public void setConfirmer(@NonNull User confirmer) {
    this.confirmer = confirmer;
  }

  /**
   * Determines whether or not the friend request has been accepted by the {@link User} who is
   * receiving the request.
   * @return A boolean value of Confirmed = true.
   */
  public boolean isConfirmed() {
    return confirmed;
  }

  /**
   * Either confirms or denies the friend request.
   * @param confirmed Yes or No to confirm request
   */
  public void setConfirmed(boolean confirmed) {
    this.confirmed = confirmed;
  }
}
