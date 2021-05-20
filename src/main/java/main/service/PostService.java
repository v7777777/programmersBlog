package main.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import main.data.dtos.DateAmountView;
import main.data.request.NewPostRequest;
import main.data.response.CalendarResponse;
import main.data.response.DetailedPostResponse;
import main.data.response.NewPostResponse;
import main.data.response.NewPostResponseErrors;
import main.data.response.PostResponse;
import main.data.response.listResponses.ListPostResponse;
import main.model.Post;
import main.model.Tag;
import main.model.User;
import main.model.enums.ModerationStatusCode;
import main.repository.PostRepository;
import main.repository.TagRepository;
import main.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postsRepository;
  private final UserRepository userRepository;
  private final TagRepository tagRepository;

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

    if (year == null) {

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

    // ЕСЛИ USER АУТЕНТИФИЦИРОВАН НАХОДИТЬ показывать ВСЕ СВОИ ПОСТЫ
    // ТЕ ПОСТ АВТОР АЙДИ РАВЕН АУТЕНТИФИЦИРОВАН АЙДИ

    Post post;
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    boolean isAuthenticated = !(auth instanceof AnonymousAuthenticationToken);

    if (isAuthenticated) {

      String email = ((org.springframework.security.core.userdetails.User) auth.getPrincipal())
          .getUsername();

      Optional<Post> postOptional = postsRepository.findAnyPostById(id);

      if (postOptional.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
      }

      post = postOptional.get();

      // если автор искомого поста авторизован то показывать пост с любым статусом

      boolean isPostAuthor = post.getUser().getEmail().equals(email);

      // не автору не показывать не активные посты

      if (!isPostAuthor &&
          (!post.isActive() ||
              !post.getModerationStatus().equals(ModerationStatusCode.ACCEPTED) ||
              post.getTime().isAfter(Instant.now()))) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
      }

      // увеличение просмотров поста

      boolean isModerator = userRepository.findByEmail(email).get().isModerator();

      if (!isPostAuthor && isModerator != true) {
        post.setViewCount(post.getViewCount() + 1);
        postsRepository.save(post);
      }

    }
    // ЕСЛИ НЕ АУТЕНТИФИЦИРОВАН НАХОДИТЬ просто ВСЕ активные ПОСТЫ
    else {

      Optional<Post> postOptional = postsRepository.findActivePostById(id);

      if (postOptional.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
      }
      post = postOptional.get();
      post.setViewCount(
          post.getViewCount() + 1);  // увеличиваем просмотры если пользователь не авторизован ?????
      postsRepository.save(post);
    }

    DetailedPostResponse detailedPostResponse = new DetailedPostResponse(post);

    return detailedPostResponse;
  }

  public ListPostResponse getMyPosts(int offset, int limit, String status) {

    int page = offset / limit;
    Pageable pageable = PageRequest.of(page, limit);

    List<PostResponse> postsResponse = new ArrayList<>();
    ListPostResponse listPostResponse = new ListPostResponse(postsResponse);

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
        .getContext()
        .getAuthentication().getPrincipal();

    if (!(securityUser instanceof org.springframework.security.core.userdetails.User)) {

      throw new AuthenticationCredentialsNotFoundException("please authorize");

    }

    User authUser = userRepository.findByEmail(
        securityUser.getUsername()).orElseThrow(() -> new UsernameNotFoundException("not found"));

    int myId = authUser.getId();

    if (status.equals("published")) {
      Page<Post> myPublishedPosts = postsRepository.findPublishedPostsById(myId, pageable);
      myPublishedPosts.forEach(p -> postsResponse.add(new PostResponse(p)));
      listPostResponse.setCount(myPublishedPosts.getTotalElements());
    } else if (status.equals("inactive")) {
      Page<Post> myInactivePosts = postsRepository.findInactivePostsById(myId, pageable);
      myInactivePosts.forEach(p -> postsResponse.add(new PostResponse(p)));
      listPostResponse.setCount(myInactivePosts.getTotalElements());
    } else if (status.equals("pending")) {
      Page<Post> myPendingPosts = postsRepository.findPendingPostsById(myId, pageable);
      myPendingPosts.forEach(p -> postsResponse.add(new PostResponse(p)));
      listPostResponse.setCount(myPendingPosts.getTotalElements());
    } else if (status.equals("declined")) {
      Page<Post> myDeclinedPosts = postsRepository.findDeclinedPostsById(myId, pageable);
      myDeclinedPosts.forEach(p -> postsResponse.add(new PostResponse(p)));
      listPostResponse.setCount(myDeclinedPosts.getTotalElements());
    }
    return listPostResponse;
  }

  public NewPostResponse post(NewPostRequest newPostRequest) {

    NewPostResponse newPostResponse = new NewPostResponse();

    if (newPostRequest.getTitle().isEmpty()) {

      NewPostResponseErrors errors = new NewPostResponseErrors();
      errors.setTitle("title is empty");
      newPostResponse.setResult(false);
      newPostResponse.setErrors(errors);
      return newPostResponse;
    }
    if (newPostRequest.getTitle().length() < 3) {
      NewPostResponseErrors errors = new NewPostResponseErrors();
      errors.setTitle("title is too short");
      newPostResponse.setResult(false);
      newPostResponse.setErrors(errors);
      return newPostResponse;
    }
    if (newPostRequest.getText().isEmpty()) {
      NewPostResponseErrors errors = new NewPostResponseErrors();
      errors.setText("text is empty");
      newPostResponse.setResult(false);
      newPostResponse.setErrors(errors);
      return newPostResponse;
    }
    if (newPostRequest.getText().length() < 50) {
      NewPostResponseErrors errors = new NewPostResponseErrors();
      errors.setText("text is too short");
      newPostResponse.setResult(false);
      newPostResponse.setErrors(errors);
      return newPostResponse;
    }

    Instant time = Instant.ofEpochMilli(newPostRequest.getTimestamp());

    if (time.isBefore(Instant.now())) {
      time = Instant.now();
    }

    Post newPost = new Post();
    newPost.setActive(newPostRequest.isActive());
    newPost.setTime(time);
    newPost.setText(newPostRequest.getText());
    newPost.setTitle(newPostRequest.getTitle());
    newPost.setModerationStatus(ModerationStatusCode.NEW);

    List<Tag> tags = new ArrayList<>();

    newPostRequest.getTags().forEach(t -> {
      {

        Tag tag;

        Optional<Tag> tagOptional = tagRepository.findByName(t);

        if (tagOptional.isEmpty()) {
          tag = new Tag();
          tag.setName(t);
          tagRepository.save(tag);
        } else {
          tag = tagOptional.get();
        }

        tags.add(tag);

      }
    });

    newPost.setTags(tags);

    String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder
        .getContext().getAuthentication()
        .getPrincipal())
        .getUsername();

    User author = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));

    newPost.setUser(author);

    postsRepository.save(newPost);

    newPostResponse.setResult(true);

    return newPostResponse;
  }

  // true = 1 = like false = 0 = dislike


}
