package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.Message;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The {@link MessageRepository} extends the {@link JpaRepository}, which provides the
 * CRUD (create, read, update, delete) functions, as well as providing the ability to delete records
 * in batches.
 */
public interface MessageRepository extends JpaRepository<Message, UUID> {


}
