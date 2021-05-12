package main.data.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
public class RegistrationResponseErrors {

  @JsonInclude(Include.NON_NULL)
  private String email;

  @JsonInclude(Include.NON_NULL)
  private String name;

  @JsonInclude(Include.NON_NULL)
  private String password;

  @JsonInclude(Include.NON_NULL)
  private String captcha;



}
