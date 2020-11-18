package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findFirstByOauthKey(String oauthKey);

  List<User> findByName(String name);
}
