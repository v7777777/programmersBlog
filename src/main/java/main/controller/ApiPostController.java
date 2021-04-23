package main.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import main.data.response.listResponses.ListPostResponse;
import main.data.response.listResponses.ListTagResponse;
import main.data.response.TagResponse;
import main.service.PostService;
import main.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiPostController {

  private final PostService postService;
  private final TagService tagService;


  @GetMapping("/post")
  public ResponseEntity<ListPostResponse> getPosts(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "10") int limit,
      @RequestParam(required = false, defaultValue = "recent") String mode) {

    return ResponseEntity.ok(postService.getPosts(offset, limit, mode));

  }

  @GetMapping("/tag")
  public ResponseEntity<ListTagResponse> getTags(@RequestParam(required = false) String query) {

    return ResponseEntity.ok(tagService.getTags(query));

  }

}