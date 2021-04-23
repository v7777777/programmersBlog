package main.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import main.data.response.listResponses.ListPostResponse;
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
public class PostService {

  private final PostRepository postsRepository;

  public ListPostResponse getPosts(int offset, int limit, String mode) {

    Pageable pageable;
    int page = offset / limit;
    List<PostResponse> postsResponse = new ArrayList<>();
    ListPostResponse listPostResponse = new ListPostResponse(postsResponse);

    if (mode.equals("recent")) {

      pageable = PageRequest.of(page, limit, Sort.by("time").descending());

      Page<Post> posts = postsRepository.findAllActive(pageable);

      posts.forEach(p -> {
        {
          PostResponse postResponse = new PostResponse();
          UserPostResponse userResponse = new UserPostResponse();
          userResponse.setId(p.getUser().getId());
          userResponse.setName(p.getUser().getName());

          String announce = getAnnounce(p.getText());
          int likeCount = calculateLikes(p);
          int dislikeCount = calculateDislikes(p);

          postResponse.setId(p.getId());
          postResponse.setTimestamp(p.getTime().getEpochSecond());
          postResponse.setUser(userResponse);
          postResponse.setTitle(p.getTitle());
          postResponse.setAnnounce(announce);
          postResponse.setLikeCount(likeCount);
          postResponse.setDislikeCount(dislikeCount);
          postResponse.setViewCount(p.getViewCount());

          postsResponse.add(postResponse);


        }
      });

      listPostResponse.setCount(posts.getTotalElements());

    }
    else if (mode.equals("popular")) {
      pageable = PageRequest.of(page, limit);

      Page<Post> posts = postsRepository.findAllByCommentsAmount(pageable);

      posts.forEach(p -> {
        {
          PostResponse postResponse = new PostResponse();
          UserPostResponse userResponse = new UserPostResponse();
          userResponse.setId(p.getUser().getId());
          userResponse.setName(p.getUser().getName());

          String announce = getAnnounce(p.getText());
          int likeCount = calculateLikes(p);
          int dislikeCount = calculateDislikes(p);

          postResponse.setId(p.getId());
          postResponse.setTimestamp(p.getTime().getEpochSecond());
          postResponse.setUser(userResponse);
          postResponse.setTitle(p.getTitle());
          postResponse.setAnnounce(announce);
          postResponse.setLikeCount(likeCount);
          postResponse.setDislikeCount(dislikeCount);
          postResponse.setViewCount(p.getViewCount());

          postsResponse.add(postResponse);


        }
      });

      listPostResponse.setCount(posts.getTotalElements());
    }
    else if (mode.equals("best")) {

      pageable = PageRequest.of(page, limit);

      Page<Post> posts = postsRepository.findAllByLikesAmount(pageable); //

      listPostResponse.setCount(posts.getTotalElements()); //

      posts.forEach(p -> {
        {
          PostResponse postResponse = new PostResponse();
          UserPostResponse userResponse = new UserPostResponse();
          userResponse.setId(p.getUser().getId());
          userResponse.setName(p.getUser().getName());

          String announce = getAnnounce(p.getText());
          int likeCount = calculateLikes(p);
          int dislikeCount = calculateDislikes(p);

          postResponse.setId(p.getId());
          postResponse.setTimestamp(p.getTime().getEpochSecond());
          postResponse.setUser(userResponse);
          postResponse.setTitle(p.getTitle());
          postResponse.setAnnounce(announce);
          postResponse.setLikeCount(likeCount);
          postResponse.setDislikeCount(dislikeCount);
          postResponse.setViewCount(p.getViewCount());

          postsResponse.add(postResponse);


        }
      });

    }
    else if (mode.equals("early")) {

      pageable = PageRequest.of(page, limit, Sort.by("time").ascending());

      Page<Post> posts = postsRepository.findAllActive(pageable);

      posts.forEach(p -> {
        {
          PostResponse postResponse = new PostResponse();
          UserPostResponse userResponse = new UserPostResponse();
          userResponse.setId(p.getUser().getId());
          userResponse.setName(p.getUser().getName());

          String announce = getAnnounce(p.getText());
          int likeCount = calculateLikes(p);
          int dislikeCount = calculateDislikes(p);

          postResponse.setId(p.getId());
          postResponse.setTimestamp(p.getTime().getEpochSecond());
          postResponse.setUser(userResponse);
          postResponse.setTitle(p.getTitle());
          postResponse.setAnnounce(announce);
          postResponse.setLikeCount(likeCount);
          postResponse.setDislikeCount(dislikeCount);
          postResponse.setViewCount(p.getViewCount());

          postsResponse.add(postResponse);


        }
      });

      listPostResponse.setCount(posts.getTotalElements());
    }

    return listPostResponse;
  }

  // true = 1 = like false = 0 = dislike

  private int calculateDislikes(Post p) {

    return (int) p.getPostVotes().stream().filter(vote -> vote.isValue() == false).count();
  }

  private int calculateLikes(Post p) {

    return (int) p.getPostVotes().stream().filter(vote -> vote.isValue() == true).count();
  }

  private String getAnnounce(String postText) {

    // длина не более 150 символов, все HTML теги должны быть удалены
    //конце полученной строки добавить троеточие ...

    String shortenString = postText.replace("\\<.*?>", "");

    if (shortenString.length() > 150) {
      shortenString = shortenString.substring(0, 150) + "...";
    }

    return shortenString;

  }

}
