package main.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class NewPostRequest {

  long timestamp;
  @JsonProperty("active")
  boolean isActive;
  String title;
  List<String> tags;
  String text;

}
