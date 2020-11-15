package edu.cnm.deepdive.powdr.model.dao;

import edu.cnm.deepdive.powdr.model.entity.Post;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, UUID> {

  Optional<List<Post>> findFirstByUser(List<Post> Post);
}
