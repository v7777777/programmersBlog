package main.repository;

import main.model.CaptchaCodes;
import main.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaptchaCodesRepository extends JpaRepository<CaptchaCodes, Posts> {

}
