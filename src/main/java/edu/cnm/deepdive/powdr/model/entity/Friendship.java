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

  public UUID getFriendshipId() {
    return friendshipId;
  }

  @NonNull
  public User getRequester() {
    return requester;
  }

  public void setRequester(@NonNull User requester) {
    this.requester = requester;
  }

  @NonNull
  public User getConfirmer() {
    return confirmer;
  }

  public void setConfirmer(@NonNull User confirmer) {
    this.confirmer = confirmer;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  public void setConfirmed(boolean confirmed) {
    this.confirmed = confirmed;
  }
}
