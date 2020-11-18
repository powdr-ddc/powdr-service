package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.Post;
import edu.cnm.deepdive.powdr.model.entity.User;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, UUID> {

  List<Post> getAllByUser(User user);

  List<Post> findPostByContentNotContains(String keyword);

  List<Post> findPostByTimestamp(Date timestamp);
}
