package main.data.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
public class NewPostResponseErrors {

  @JsonInclude(Include.NON_NULL)
  private String title;

  @JsonInclude(Include.NON_NULL)
  private String text;

}
