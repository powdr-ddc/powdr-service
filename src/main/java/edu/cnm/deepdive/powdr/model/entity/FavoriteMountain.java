package edu.cnm.deepdive.powdr.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    uniqueConstraints =
        @UniqueConstraint(columnNames = {"user_id", "mountain_id"})
)
public class FavoriteMountain {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "favorite_mountain_id", nullable = false, updatable = false)
  private Long id;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "mountain_id", nullable = false, updatable = false)
  private Mountain mountain;


  public Long getId() {
    return id;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }

  @NonNull
  public Mountain getMountain() {
    return mountain;
  }

  public void setMountain(@NonNull Mountain mountain) {
    this.mountain = mountain;
  }
}
