package main.repository;

import main.model.GlobalSettings;
import main.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Posts> {

}
