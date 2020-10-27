package edu.cnm.deepdive.powdr.model.entity;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false, updatable = false)
  private Long userId;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true)
  @JsonIgnore
  private String oauthKey;

  @Nullable
  private String bio;

  @NonNull
  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("timestamp DESC")
  private List<Post> posts;

  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "mountain_id"))
  @OrderBy("name ASC")
  private List<Mountain> mountains = new LinkedList<>();

  //private BLOB PICTURE


  public List<Mountain> getMountains() {
    return mountains;
  }

  public Long getUserId() {
    return userId;
  }

  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  public List<Post> getPosts() {
    return posts;
  }
}
