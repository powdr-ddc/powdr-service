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

/**
 * Message entity to store information in regard to the messages a user may send to other people
 * such as the sender/receiver, and the time it was created.
 */
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

  /**
   * Retrieves the UUID of a specific {@link Message}.
   * @return UUID of a Message.
   */
  public UUID getMessageId() {
    return messageId;
  }

  /** Retrieves the {@link User} that sent the message.
   * @return The User that sent the message.
   */
  @NonNull
  public User getSender() {
    return sender;
  }

  /**
   * Sets the current {@link User} as the sender of the message
   * @param sender The current User sending the message.
   */
  public void setSender(@NonNull User sender) {
    this.sender = sender;
  }

  /** Retrieves the {@link User} that received the message.
   * @return The User that received the message.
   */
  @NonNull
  public User getReceiver() {
    return receiver;
  }

  /**
   * Sets the current {@link User} as the receiver of the message
   * @param receiver The current User receiving the message.
   */
  public void setReceiver(@NonNull User receiver) {
    this.receiver = receiver;
  }

  /**
   * Retrieves the contents of a specific {@link Message}.
   * @return returns the content of a Message.
   */
  @NonNull
  public String getContent() {
    return content;
  }

  /**
   * Retrieves the timestamp at which a {@link Message} was sent.
   * @return A timestamp for a Message.
   */
  @NonNull
  public Date getTimestamp() {
    return timestamp;
  }

}
