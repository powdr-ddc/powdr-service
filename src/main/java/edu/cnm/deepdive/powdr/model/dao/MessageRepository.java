package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.Message;
import edu.cnm.deepdive.powdr.model.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The {@link MessageRepository} extends the {@link JpaRepository}, which provides the
 * CRUD (create, read, update, delete) functions, as well as providing the ability to delete records
 * in batches.
 */
public interface MessageRepository extends JpaRepository<Message, UUID> {

  /**
   * Gets all messages between the specified sender or receiver.
   *
   * @param sender {@link User} who sent the initial message.
   * @param receiver {@link User} who receives the initial message.
   * @return A list of messages between the two users.
   */
  List<Message> getAllBySenderOrReceiver(User sender, User receiver);
}
