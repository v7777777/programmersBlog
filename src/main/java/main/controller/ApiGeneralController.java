package main.controller;

import lombok.RequiredArgsConstructor;
import main.data.response.InitResponse;
import main.data.response.SettingsResponse;
import main.service.SettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class ApiGeneralController {

  private final InitResponse initResponse;
  private final SettingService settingService;

  @GetMapping("init")
  public ResponseEntity<InitResponse> init(){

    return ResponseEntity.ok(initResponse);

  }

  @GetMapping("settings")
  public ResponseEntity<SettingsResponse> settings(){

    return ResponseEntity.ok(settingService.getSettings());

  }

}