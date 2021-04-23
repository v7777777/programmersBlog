package main.service;

import lombok.RequiredArgsConstructor;
import main.data.response.SettingsResponse;
import main.model.GlobalSetting;
import main.model.enums.SettingValue;
import main.repository.GlobalSettingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingService {

  private final GlobalSettingRepository settingsRepository;

  // потокобезопастность если 2 модератора меняют ???
  // GlobalSettings это таблица где (всего 3 строки) каждая строка эта настройка --- id, enum (code-name), eam (yes-no)

  // id    code                      name                                  value

  //  1    MULTIUSER_MODE         Многопользовательский режим            YES / NO
  //  2    POST_PREMODERATION     Премодерация постов                    YES / NO
  //  3    STATISTICS_IS_PUBLIC   Показывать всем статистику блога       YES / NO

  public SettingsResponse getSettings() {

    GlobalSetting multiuserMode = settingsRepository.findByCode("MULTIUSER_MODE");
    GlobalSetting postPremoderation = settingsRepository.findByCode("POST_PREMODERATION");
    GlobalSetting statisticsIsPublic = settingsRepository.findByCode("STATISTICS_IS_PUBLIC");

    SettingsResponse settingsResponse = new SettingsResponse();

    settingsResponse.setMultiuserMode(convertSettingValueToBoolean(multiuserMode.getValue()));
    settingsResponse
        .setPostPremoderation(convertSettingValueToBoolean(postPremoderation.getValue()));
    settingsResponse
        .setStatisticsIsPublic(convertSettingValueToBoolean(statisticsIsPublic.getValue()));

    return settingsResponse;

  }

  private boolean convertSettingValueToBoolean(SettingValue value) {

    if (value.equals(SettingValue.YES)) {
      return true;
    } else {
      return false;
    }
  }

}
