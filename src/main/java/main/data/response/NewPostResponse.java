package main.data.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
public class NewPostResponse {

  private boolean result;
  @JsonInclude(Include.NON_NULL)
  private NewPostResponseErrors errors;

}
