package main.repository;

import java.util.List;
import main.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {

  List<Tag> findAll();

  List<Tag> findByNameStartingWith(String prefix);


}
