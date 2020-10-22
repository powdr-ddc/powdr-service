package edu.cnm.deepdive.powdr.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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

  @Nullable
  private String post;

  @NonNull
  @Column(nullable = false)
  private String name;

  //private BLOB PICTURE

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

  public String getPost() {
    return post;
  }

  public void setPost(String post) {
    this.post = post;
  }
}
