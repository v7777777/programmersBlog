package main.repository;

import main.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {

  // Должны выводиться только активные (поле is_active в таблице posts равно 1), утверждённые
  //модератором (поле moderation_status равно ACCEPTED) посты с датой публикации не позднее
  //текущего момента (движок должен позволять откладывать публикацию).

  @Query(nativeQuery = true, value =
      "select count(*) from posts where is_active = 1 && moderation_status = 'accepted' && time <= NOW()")
  int countAllActivePosts();


  @Query(nativeQuery = true, value =
      "select * from posts where is_active = 1 && moderation_status = 'accepted' && time <= NOW()",
      countQuery = "select count(*) from posts where is_active = 1 && moderation_status = 'accepted' && time <= NOW()")
  Page<Post> findAllActive(Pageable pageable);


  @Query(nativeQuery = true, value =
      "select posts.*, COUNT(post_comments.id) as post_comments_count from posts "
          + "left join post_comments on post_comments.post_id = posts.id "
          + "where posts.is_active = 1 and posts.moderation_status = 'accepted' and posts.time <= NOW()  "
          + "group by posts.id "
          + "order by post_comments_count DESC ",
      countQuery = "select count(*) from posts where is_active = 1 && moderation_status = 'accepted' && time <= NOW()")
  Page<Post> findAllByCommentsAmount(Pageable pageable);

  @Query(nativeQuery = true, value =
      "select posts.*,  "
          + "COUNT(case when post_votes.value = 1 && post_votes.post_id = posts.id then 1 else null end) as post_votes_likes, "
          + "COUNT(case when post_votes.value = 0 && post_votes.post_id = posts.id then 1 else null end) as post_votes_dislikes "
          + "from posts "
          + "left join post_votes on post_votes.post_id = posts.id "
          + "where posts.is_active = 1 and posts.moderation_status = 'accepted' and posts.time <= NOW()  "
          + "group by posts.id "
          + "order by post_votes_likes DESC, post_votes_dislikes ASC",
      countQuery = "select count(*) from posts where is_active = 1 && moderation_status = 'accepted' && time <= NOW()")
  Page<Post> findAllByLikesAmount(Pageable pageable);

}
