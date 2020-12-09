package edu.cnm.deepdive.powdr.model.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

/**
 * Ski Resort entity in the database to store information regarding a ski resort such as the ski
 * resort name, and the latitude and longitude to pinpoint a specific ski resort, since multiple
 * resorts can be on a single mountain.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "latitude"),
        @Index(columnList = "longitude")
    }
)
public class SkiResort {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(nullable = false, updatable = false,
      columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID skiResortId;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true, length = 100)
  private String name;

  @NonNull
  @OneToMany(mappedBy = "skiResort", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<FavoriteSkiResort> favoriteSkiResorts = new LinkedList<>();

  private double latitude;

  private double longitude;

  /**
   * Gets The UUID of a {@link SkiResort}.
   * @return Returns UUID of SkiResort.
   */
  public UUID getSkiResortId() {
    return skiResortId;
  }

  /**
   * Gets the name of a {@link SkiResort}.
   * @return Returns a string of a name.
   */
  @NonNull
  public String getName() {
    return name;
  }

  /**
   * Sets the name of a {@link SkiResort}.
   * @param name Name of ski resort.
   */
  public void setName(@NonNull String name) {
    this.name = name;
  }

  /**
   * Gets the latitude of a {@link SkiResort}.
   * @return Returns a double of the latitude.
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Sets the latitude based on the users input.
   * @param latitude The latitude of the SkiResort.
   */
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  /**
   * Gets the longitude of a {@link SkiResort}.
   * @return Returns a double of the longitude.
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Sets the longitude based on the users input.
   * @param longitude The longitude of the SkiResort.
   */
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  /**
   * Retrieves a list of {@link FavoriteSkiResort}.
   * @return Returns a list of FavoriteSkiResort.
   */
  @NonNull
  public List<FavoriteSkiResort> getFavoriteSkiResorts() {
    return favoriteSkiResorts;
  }
}
