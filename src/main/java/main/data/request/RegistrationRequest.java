package main.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegistrationRequest {

  @JsonProperty("e_mail")
  String email;
  String password;
  String name;
  String captcha;
  @JsonProperty("captcha_secret")
  String captchaSecret;


}
