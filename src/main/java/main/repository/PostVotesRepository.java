package main.repository;

import main.model.PostVotes;
import main.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostVotesRepository extends JpaRepository<PostVotes, Posts> {

}
