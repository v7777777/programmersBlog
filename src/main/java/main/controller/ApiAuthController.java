package main.controller;

import lombok.RequiredArgsConstructor;
import main.data.request.RegistrationRequest;
import main.data.response.AuthCheckResponse;
import main.data.response.CaptchaResponse;
import main.data.response.RegistrationResponse;
import main.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class ApiAuthController {

  private final AuthService authService;

  @GetMapping("check")
  public ResponseEntity<AuthCheckResponse> check() {

    AuthCheckResponse authCheckResponse = new AuthCheckResponse();
    authCheckResponse.setUserAuthenticated(false);

    //  public DetailedPostResponse getPostById(int id) ------------ дописать проверку авторизации PostService view count

    return ResponseEntity.ok(authCheckResponse);

  }

  @GetMapping("captcha")
  public ResponseEntity<CaptchaResponse> captcha() {

    return ResponseEntity.ok(authService.captcha());

  }

  @PostMapping("register")
  public ResponseEntity<RegistrationResponse> register(
      @RequestBody RegistrationRequest registrationRequest) {

    return ResponseEntity.ok(authService.register(registrationRequest));

  }
}


