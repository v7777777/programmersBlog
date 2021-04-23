package main.controller;

import lombok.AllArgsConstructor;
import main.data.response.ListPostResponse;
import main.service.PostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post/")
@AllArgsConstructor
public class ApiPostController {

  private final PostsService postsService;

  @GetMapping
  public ResponseEntity<ListPostResponse> getPosts(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "10") int limit,
      @RequestParam(required = false, defaultValue = "recent") String mode) {

    return ResponseEntity.ok(postsService.getPosts(offset, limit, mode));

  }


}