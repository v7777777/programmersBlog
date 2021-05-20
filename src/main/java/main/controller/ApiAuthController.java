package main.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import main.data.request.LoginRequest;
import main.data.request.RegistrationRequest;
import main.data.response.CaptchaResponse;
import main.data.response.LoginResponse;
import main.data.response.RegistrationResponse;
import main.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

  @PostMapping("login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

    return ResponseEntity.ok(authService.login(loginRequest));

  }

  @GetMapping("logout")
  @PreAuthorize("hasAuthority('user:write')")
  public ResponseEntity<LoginResponse> logout() {

    SecurityContextHolder.getContext().setAuthentication(null);

    return ResponseEntity.ok(new LoginResponse(true));
  }

  @GetMapping("check")
  public ResponseEntity<LoginResponse> check(Principal principal) {

    return ResponseEntity.ok(authService.check(principal));

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


