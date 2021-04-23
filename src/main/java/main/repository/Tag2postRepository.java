package main.repository;

import java.util.List;
import main.model.Tag2post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Tag2postRepository extends JpaRepository<Tag2post, Integer> {
  List<Tag2post> findByPostId(int postId);
}
