package main.controller;

import lombok.RequiredArgsConstructor;
import main.data.request.NewPostRequest;
import main.data.response.CalendarResponse;
import main.data.response.DetailedPostResponse;
import main.data.response.NewPostResponse;
import main.data.response.listResponses.ListPostResponse;
import main.data.response.listResponses.ListTagResponse;
import main.service.PostService;
import main.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiPostController {

  private final PostService postService;
  private final TagService tagService;


  @GetMapping("/post")
 // @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<ListPostResponse> getPosts(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "10") int limit,
      @RequestParam(required = false, defaultValue = "recent") String mode) {

    return ResponseEntity.ok(postService.getPosts(offset, limit, mode));

  }

  @GetMapping("/post/search")
  //@PreAuthorize("hasAuthority('user:moderate')")
  public ResponseEntity<ListPostResponse> searchPosts(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "10") int limit,
      @RequestParam(required = false, defaultValue = "recent") String query) {

    return ResponseEntity.ok(postService.searchPosts(offset, limit, query));

  }

  @GetMapping("/tag")
  public ResponseEntity<ListTagResponse> getTags(@RequestParam(required = false) String query) {

    return ResponseEntity.ok(tagService.getTags(query));

  }

  @GetMapping("/calendar")
  public ResponseEntity<CalendarResponse> calendar(@RequestParam(required = false) String year) {

    return ResponseEntity.ok(postService.calendar(year));
  }


  @GetMapping("/post/byDate")
  public ResponseEntity<ListPostResponse> getPostsByDate(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "10") int limit,
      @RequestParam String date) {

    return ResponseEntity.ok(postService.getPostsByDate(offset, limit, date));

  }

  @GetMapping("/post/byTag")
  public ResponseEntity<ListPostResponse> getPostsByTag(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "10") int limit,
      @RequestParam String tag) {

    return ResponseEntity.ok(postService.getPostsByTag(offset, limit, tag));

  }

  @GetMapping("/post/{id}")
  public ResponseEntity<DetailedPostResponse> getPostById(
      @PathVariable int id) {

    return ResponseEntity.ok(postService.getPostById(id));

  }

  @GetMapping("/post/my")
  @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<ListPostResponse> getMyPosts(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "10") int limit,
      @RequestParam(required = false, defaultValue = "inactive") String status
  ) {

    return ResponseEntity.ok(postService.getMyPosts(offset, limit, status));

  }

  @PostMapping("/post")
  @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<NewPostResponse> newPost(@RequestBody NewPostRequest newPost) {

    return ResponseEntity.ok(postService.post(newPost));

  }


}