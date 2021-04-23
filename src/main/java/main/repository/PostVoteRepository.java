package main.repository;

import main.model.PostVote;
import main.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostVoteRepository extends JpaRepository<PostVote, Integer> {

}
