package edu.cnm.deepdive.powdr.model.entity;

import com.sun.istack.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Friendship {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "friendship_id", nullable = false, updatable = false)
  private Long friendshipId;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "requester_id", nullable = false, updatable = false)
  private Long requesterId;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "confirmer_id", nullable = false, updatable = false)
  private Long confirmerId;

  @Nullable
  private boolean confirmed;

  public Long getFriendshipId() {
    return friendshipId;
  }

  public Long getRequesterId() {
    return requesterId;
  }

  public Long getConfirmerId() {
    return confirmerId;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  @NonNull
  public void setConfirmed(boolean confirmed) {
    this.confirmed = confirmed;
  }

}
