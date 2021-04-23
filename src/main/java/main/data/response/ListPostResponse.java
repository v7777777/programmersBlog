package main.data.response;

import java.util.List;
import lombok.Data;

@Data
public class ListPostResponse {

  private int count;

  private List<PostResponse> posts;

  public ListPostResponse(List<PostResponse> posts) {
    this.posts = posts;
    count = posts.size();
  }

}
