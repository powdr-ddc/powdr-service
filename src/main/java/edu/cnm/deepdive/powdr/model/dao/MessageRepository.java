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
   * Gets all messages between two specific users
   * @param sender An instance of user for the person who sent the message
   * @param receiver An instance of user for the person who receives the message
   * @return A list of {@link Message}
   */
  List<Message> getAllBySenderOrReceiver(User sender, User receiver);
}
