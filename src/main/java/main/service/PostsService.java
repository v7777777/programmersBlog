package main.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import main.data.response.ListPostResponse;
import main.data.response.PostResponse;
import main.data.response.UserPostResponse;
import main.model.Post;
import main.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsService {

  private final PostRepository postsRepository;
  private Object SortDirection;

  public ListPostResponse getPosts(int offset, int limit, String mode) {

    // recent  сортировать по дате публикации, выводить сначала новые
    // popular сортировать по убыванию количества комментариев (посты без комментариев
    // выводить)
    // best сортировать по убыванию количества лайков (посты без лайков и дизлайков
    // выводить)
    //  early сортировать по дате публикации, выводить сначала старые

    Pageable pageable = PageRequest.of(0, 10, Sort.by("date").descending());

    Page<Post> posts = postsRepository.findAll(pageable);

    List<PostResponse> postsResponse = new ArrayList<>();

//    posts.forEach(p -> {
//      {
//        PostResponse postResponse = new PostResponse();
//        UserPostResponse userResponse = new UserPostResponse();
//        userResponse.setId(p.getUserId().getId());
//        userResponse.setName(p.getUserId().getName());
//
//        //  TO DO
//        // ДОПИСАТЬ ЭНАУНС КАК СДЕЛАТЬ И ПОСЧИТАТЬ ЛАЙКИ И ДИСЛАЙКИ
//        // СДЕЛАТЬ СОРТИРОВКУ ПО ВСЕМ КРИТЕРИЯМ
//
//        String announce = ""; // p.getText()
//        // announce - предпросмотр поста, длина не более 150 символов, все HTML теги должны быть удалены, в
//        // конце полученной строки добавить троеточие ...
//
//        // p.getPostVotes() посчитать лайки и дислайки
//
//        postResponse.setId(p.getId());
//        postResponse.setTimestamp(p.getTime().getEpochSecond());
//        postResponse.setUser(userResponse);
//        postResponse.setTitle(p.getTitle());
//        postResponse.setAnnounce(announce);
//        postResponse.setLikeCount(1);
//        postResponse.setDislikeCount(1);
//        postResponse.setViewCount(p.getViewCount());
//
//        postsResponse.add(postResponse);
//
//
//      }
//    });

    return new ListPostResponse(postsResponse);

  }


}
