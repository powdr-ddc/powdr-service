package edu.cnm.deepdive.powdr.model.entity;

import com.sun.istack.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Friendship {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "friendship_id", nullable = false, updatable = false)
  private Long friendshipId;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User requester;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User confirmer;

  private boolean confirmed;

  public Long getFriendshipId() {
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
