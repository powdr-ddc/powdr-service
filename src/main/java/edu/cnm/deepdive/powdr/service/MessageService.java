package edu.cnm.deepdive.powdr.service;

import edu.cnm.deepdive.powdr.model.dao.MessageRepository;
import edu.cnm.deepdive.powdr.model.entity.Message;
import edu.cnm.deepdive.powdr.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for {@link Message} that will hold the business logic for the entity of saving,
 * getting one or all instances of the entity, and deleting an instance of the entity.
 */
@Service
public class MessageService {

  private final MessageRepository messageRepository;

  /**
   * Constructs an instance of {@link MessageRepository}.
   * @param messageRepository An object of {@link MessageRepository}.
   */
  @Autowired
  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  /**
   * Get's a message between two users, if a message does not exist, then create a message between
   * the two users.
   *
   * @param receiver {@link User} who received the message.
   * @param sender User who sent the message.
   * @param id ID of the message in the database
   * @return The message between the two users, or save the message if one does not exist.
   */
  public Message getOrCreate(User receiver, User sender, UUID id) {
    return messageRepository.findById(id)
        .orElseGet(() -> {
          Message message = new Message();
          message.setReceiver(receiver);
          message.setSender(sender);
          return messageRepository.save(message);
        });
  }

  /**
   * Gets a message according to it's ID in the database.
   *
   * @param id Message ID in the database.
   * @return {@link Message}
   */
  public Optional<Message> get(UUID id) {
    return messageRepository.findById(id);
  }

  /**
   * Gets all messages by specified sender or receiver.
   *
   * @param sender {@link User} who sent the message.
   * @param receiver User who receives the message.
   * @return A list of messages between the two users.
   */
  public List<Message> getAllBySenderOrReceiver(User sender, User receiver) {
    return messageRepository.getAllBySenderOrReceiver(sender, receiver);
  }

  /**
   * Deletes a message.
   * @param message Message to be deleted.
   */
  public void delete(Message message) {
    messageRepository.delete(message);
  }
}
