package main.repository;

import main.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags, Integer> {

}
