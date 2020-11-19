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
 * Trip entity in the database to store information regarding a trip for a user such as the start
 * time and end time of a trip to log the total number of days on the mountain, and distance
 * traveled to see the total distance logged for a trip.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "startTime"),
        @Index(columnList = "endTime"),
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
  @CreationTimestamp // no creation timestamp; need setter
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date startTime;

  @NonNull
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date endTime;

  @NonNull
  @Column(nullable = false)
  private float distance;

  /**
   * Gets the UUID of a {@link Trip}
   * @return Returns the UUID of Trip.
   */
  public UUID getTripId() {
    return tripId;
  }

  /**
   * Gets the {@link User} that owns the trip.
   * @return Returns the user.
   */
  @NonNull
  public User getUser() {
    return user;
  }

  /**
   * Sets the {@link User} that owns the trip.
   * @param user The current {@link User}
   */
  public void setUser(@NonNull User user) {
    this.user = user;
  }

  /**
   * Gets start time of a {@link Trip}
   * @return Returns the start time of the trip.
   */
  @NonNull
  public Date getStartTime() {
    return startTime;
  }

  /**
   * Gets the end time of a {@link Trip}
   * @return Returns the end time of the trip.
   */
  @NonNull
  public Date getEndTime() {
    return endTime;
  }

  /**
   * Gets the distance travelled in a {@link Trip}
   * @return Returns the distance of a trip.
   */
  public float getDistance() {
    return distance;
  }

}
