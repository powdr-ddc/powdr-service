package edu.cnm.deepdive.powdr.model.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
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
        @Index(columnList = "timestamp")
    }
)
public class Message {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(nullable = false, updatable = false,
      columnDefinition = "CHAR(16) FOR BIT DATA")
  private UUID messageId;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "sender_id", nullable = false, updatable = false)
  private User sender;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "receiver_id", nullable = false, updatable = false)
  private User receiver;

  @NonNull
  @Column(nullable = false, updatable = false)
  private String content;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date timestamp;

  public UUID getMessageId() {
    return messageId;
  }

  @NonNull
  public User getSender() {
    return sender;
  }

  public void setSender(@NonNull User sender) {
    this.sender = sender;
  }

  @NonNull
  public User getReceiver() {
    return receiver;
  }

  public void setReceiver(@NonNull User receiver) {
    this.receiver = receiver;
  }

  @NonNull
  public String getContent() {
    return content;
  }

  @NonNull
  public Date getTimestamp() {
    return timestamp;
  }

}
