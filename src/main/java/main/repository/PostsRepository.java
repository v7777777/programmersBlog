package main.repository;

import main.model.Posts;
import main.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Users, Posts> {

}
