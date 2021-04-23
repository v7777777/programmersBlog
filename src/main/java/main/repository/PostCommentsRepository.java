package main.repository;

import main.model.PostComments;
import main.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentsRepository extends JpaRepository<PostComments, Posts> {

}
