package edu.cnm.deepdive.powdr.model.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

@Entity
@Table(
    indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "timestamp")
    }
)
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false, updatable = false)
  private Long postId;

  @NonNull
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, updatable = false)
  private Long userId;

  @NonNull
  @Column(nullable = false)
  private String content;

  // private pictures

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @OrderBy("order DESC")
  private Date timestamp;

  public Long getPostId() {
    return postId;
  }

  @NonNull
  public Long getUserId() {
    return userId;
  }


  @NonNull
  public String getContent() {
    return content;
  }

  public void setContent(@NonNull String content) {
    this.content = content;
  }

  @NonNull
  public Date getTimestamp() {
    return timestamp;
  }

}
