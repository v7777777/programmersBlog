package main.repository;

import java.util.List;
import main.model.GlobalSetting;
import main.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalSettingRepository extends JpaRepository<GlobalSetting, Integer> {

  GlobalSetting findByCode (String code);


}
