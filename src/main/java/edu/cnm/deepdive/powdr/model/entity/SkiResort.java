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
import javax.persistence.OneToMany;
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
public class SkiResort {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false, updatable = false)
  private Long skiResortId;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true, length = 100)
  private String name;

  @OneToMany(mappedBy = "ski_resort", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<FavoriteSkiResort> favoriteSkiResorts = new LinkedList<>();

  private double latitude;

  private double longitude;

  public Long getSkiResortId() {
    return skiResortId;
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

  public List<FavoriteSkiResort> getFavoriteSkiResorts() {
    return favoriteSkiResorts;
  }
}
