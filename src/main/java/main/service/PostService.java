package main.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import main.data.dtos.DateAmountView;
import main.data.response.CalendarResponse;
import main.data.response.DetailedPostResponse;
import main.data.response.PostResponse;
import main.data.response.listResponses.ListPostResponse;
import main.model.Post;
import main.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

      posts.forEach(p -> postsResponse.add(new PostResponse(p)));

      listPostResponse.setCount(posts.getTotalElements());

    } else if (mode.equals("popular")) {
      pageable = PageRequest.of(page, limit);

      Page<Post> posts = postsRepository.findAllByCommentsAmount(pageable);

      posts.forEach(p -> postsResponse.add(new PostResponse(p)));

      listPostResponse.setCount(posts.getTotalElements());
    } else if (mode.equals("best")) {

      pageable = PageRequest.of(page, limit);

      Page<Post> posts = postsRepository.findAllByLikesAmount(pageable); //

      listPostResponse.setCount(posts.getTotalElements()); //

      posts.forEach(p -> postsResponse.add(new PostResponse(p)));

    } else if (mode.equals("early")) {

      pageable = PageRequest.of(page, limit, Sort.by("time").ascending());

      Page<Post> posts = postsRepository.findAllActive(pageable);

      posts.forEach(p -> postsResponse.add(new PostResponse(p)));

      listPostResponse.setCount(posts.getTotalElements());
    }

    return listPostResponse;
  }

  public ListPostResponse searchPosts(int offset, int limit, String query) {

    Pageable pageable;
    int page = offset / limit;

    List<PostResponse> postsResponse = new ArrayList<>();
    ListPostResponse listPostResponse = new ListPostResponse(postsResponse);

    //случае, если запрос
    //пустой или содержит только пробелы, метод должен выводить все посты (запрос GET /api/post c
    //параметров mode=recent)

    if (query.equals("recent") || query.matches("\\s*")) {

      pageable = PageRequest.of(page, limit, Sort.by("time").descending());

      Page<Post> posts = postsRepository.findAllActive(pageable);

      posts.forEach(p -> postsResponse.add(new PostResponse(p)));

      listPostResponse.setCount(posts.getTotalElements());
    } else {

      pageable = PageRequest.of(page, limit);
      query = "%" + query + "%";
      Page<Post> posts = postsRepository.findByTextOrTitle(query, pageable);
      posts.forEach(p -> postsResponse.add(new PostResponse(p)));
      listPostResponse.setCount(posts.getTotalElements());
    }

    return listPostResponse;
  }

  public CalendarResponse calendar(String year) {

    List<Integer> yearsResponse = postsRepository.getYearsWithActivePosts();

    CalendarResponse calendarResponse = new CalendarResponse();

    HashMap<LocalDate, Integer> postsResponse = new HashMap<>();

    int currentYear;

    //если не передан - возвращать за текущий год

    if (year.equals(null)) {

      currentYear = LocalDate.now().getYear();
    } else {
      currentYear = Integer.parseInt(year);
    }

    List<DateAmountView> result = postsRepository.getStatisticsPostsFromYear(currentYear);

    result.forEach(r -> postsResponse.put(r.getTime(), r.getCount()));

    calendarResponse.setPosts(postsResponse);
    calendarResponse.setYears(yearsResponse);

    return calendarResponse;
  }

  public ListPostResponse getPostsByDate(int offset, int limit, String date) {

    // date - дата в формате "2019-10-15"

    int page = offset / limit;
    Pageable pageable = PageRequest.of(page, limit, Sort.by("time").descending());

    List<PostResponse> postsResponse = new ArrayList<>();
    ListPostResponse listPostResponse = new ListPostResponse(postsResponse);

    Page<Post> postsByDate = postsRepository.findAllActivePostsByDate(pageable, date);

    postsByDate.forEach(p -> postsResponse.add(new PostResponse(p)));

    listPostResponse.setCount(postsByDate.getTotalElements());

    return listPostResponse;

  }

  public ListPostResponse getPostsByTag(int offset, int limit, String tag) {

    // date - дата в формате "2019-10-15"

    int page = offset / limit;
    Pageable pageable = PageRequest.of(page, limit);
    ;
    List<PostResponse> postsResponse = new ArrayList<>();
    ListPostResponse listPostResponse = new ListPostResponse(postsResponse);

    Page<Post> postsByTag = postsRepository.findAllActivePostsByTag(pageable, tag);

    postsByTag.forEach(p -> postsResponse.add(new PostResponse(p)));

    listPostResponse.setCount(postsByTag.getTotalElements());

    return listPostResponse;

  }

  public DetailedPostResponse getPostById(int id) {

    Optional<Post> postOptional = postsRepository.findActivePostById(id);

    if (postOptional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
    }

    Post post = postOptional.get();

    //При успешном запросе необходимо увеличивать количество просмотров поста на 1 (поле view_count),
    //кроме случаев:
    //Если модератор авторизован, то не считаем его просмотры вообще
    //Если автор авторизован, то не считаем просмотры своих же публикаций
    // ---------- ДОПИСАТЬ!!!
    // --------------- ЕСЛИ НЕ (проверка автор поста = id авторизованного пользователя ИЛИ авторизован модератор)  if(!)

    post.setViewCount(post.getViewCount() + 1);

    postsRepository.save(post);

    DetailedPostResponse detailedPostResponse = new DetailedPostResponse(post);

    return detailedPostResponse;
  }

  // true = 1 = like false = 0 = dislike


}
