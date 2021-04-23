package main.data.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthCheckResponse {

  @JsonProperty("result")
  private boolean isUserAuthenticated;

  @JsonInclude(Include.NON_NULL)
  private UserResponse user;

}
