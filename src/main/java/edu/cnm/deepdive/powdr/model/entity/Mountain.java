package edu.cnm.deepdive.powdr.model.entity;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "latitude"),
        @Index(columnList = "longitude")
    }
)
public class Mountain {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false, updatable = false)
  private Long mountainId;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true, length = 100)
  private String name;

  private double latitude;

  private double longitude;

  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(joinColumns = @JoinColumn(name = "mountain_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  @OrderBy("name ASC")
  private List<User> users = new LinkedList<>();

  public List<User> getUsers() {
    return users;
  }

  public Long getMountainId() {
    return mountainId;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }
}
