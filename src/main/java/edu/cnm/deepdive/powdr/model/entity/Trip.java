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

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "start_time"),
        @Index(columnList = "end_time"),
        @Index(columnList = "distance")
    }
)
public class Trip {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(nullable = false, updatable = false, columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID tripId;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date start;

  @NonNull
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date end;

  @NonNull
  @Column(nullable = false)
  private float distance;

  public UUID getTripId() {
    return tripId;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }

  @NonNull
  public Date getStart() {
    return start;
  }

  @NonNull
  public Date getEnd() {
    return end;
  }

  public float getDistance() {
    return distance;
  }

}
