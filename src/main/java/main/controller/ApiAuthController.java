package main.controller;

import main.data.response.AuthCheckResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class ApiAuthController {

  @GetMapping("check")
  public ResponseEntity<AuthCheckResponse> check(){

    AuthCheckResponse authCheckResponse = new AuthCheckResponse();
    authCheckResponse.setUserAuthenticated(false);

    return ResponseEntity.ok(authCheckResponse);

  }
}


