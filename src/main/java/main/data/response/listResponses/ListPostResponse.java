package main.data.response.listResponses;

import java.util.List;
import lombok.Data;
import main.data.response.PostResponse;

@Data
public class ListPostResponse {

  private long count;

  private List<PostResponse> posts;

  public ListPostResponse(List<PostResponse> posts) {
    this.posts = posts;

  }

}
