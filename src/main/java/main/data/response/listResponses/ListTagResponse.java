package main.data.response.listResponses;

import java.util.List;
import lombok.Data;
import main.data.response.TagResponse;

@Data
public class ListTagResponse {

  private List<TagResponse> tags;

  public ListTagResponse(List<TagResponse> tags) {
    this.tags = tags;

  }

}
