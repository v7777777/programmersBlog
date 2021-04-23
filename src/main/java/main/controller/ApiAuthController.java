package main.controller;

import main.model.GlobalSettings;
import main.model.GlobalSettingsValues;
import main.model.SettingValue;
import main.repository.GlobalSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class ApiAuthController {

  // TEST Persisting Enums Talend http://localhost:8080/api/auth post

//  @Autowired
//  GlobalSettingsRepository globalSettingsRepository;
//
//  @PostMapping("/api/auth")
//  public void add() {
//
//    GlobalSettings gs = new GlobalSettings();
//
//    gs.setValue(SettingValue.NO);
//    gs.setGlobalSettingsValues(GlobalSettingsValues.MULTIUSER_MODE);
//    globalSettingsRepository.save(gs);


  }


