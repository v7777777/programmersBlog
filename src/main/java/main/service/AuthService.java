package main.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import main.data.request.RegistrationRequest;
import main.data.response.CaptchaResponse;
import main.data.response.RegistrationResponse;
import main.data.response.RegistrationResponseErrors;
import main.model.CaptchaCode;
import main.model.User;
import main.repository.CaptchaCodeRepository;
import main.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final CaptchaCodeRepository captchaCodeRepository;
  private final UserRepository userRepository;

  @Value("${delete.expiredCaptcha}")
  public String deleteExpiredCaptchaTime;

  public CaptchaResponse captcha() {

    //Также метод должен удалять устаревшие капчи из таблицы. Время устаревания должно быть задано в
    //конфигурации приложения (по умолчанию, 1 час).

    int deleteTime = 60;

    if (!deleteExpiredCaptchaTime.isEmpty()) {
      deleteTime = Integer.parseInt(deleteExpiredCaptchaTime);
    }

    captchaCodeRepository.deleteExpiredCaptchas(deleteTime);

    CaptchaResponse captchaResponse = new CaptchaResponse();
    CaptchaCode captchaCode = new CaptchaCode();

    Cage cage = new GCage();
    String token = cage.getTokenGenerator().next();  // token generator to produce strings for the image
    byte[] imageByte = cage.draw(token); // Generate an image and return it in a byte array.

    String encodedString = Base64.getEncoder().encodeToString(imageByte);
    String encodedStringWithPrefix = "data:image/png;base64, " + encodedString;
    String secret = (RandomStringUtils.randomAlphanumeric(22)).toLowerCase();

    // ---------проверить на уникальность ?? !! сделать запрос цикл вайл ??

    captchaCode.setSecretCode(secret);
    captchaCode.setCode(token);
    captchaCode.setTime(Instant.now());

    captchaCodeRepository.save(captchaCode);

    captchaResponse.setSecret(secret);
    captchaResponse.setImage(encodedStringWithPrefix);

    return captchaResponse;
  }

  public RegistrationResponse register(RegistrationRequest request) {

    // после того как пользователь
    //вводит данные каптчи, отправляется форма содержащая текст-расшифровка каптчи пользователем и
    //secret. Сервис ищет по значению secret запись о каптче в таблице и сравнивает ввод пользователя
    //со значением поля code таблицы captcha_codes. На основе сравнения решается - каптча введена
    //верно или нет.

    // response captcha - код на рисунке / secret возвращает полученную строку
    // БД code - код на рисунке / secret_code - строка

    // удалить старые каптчи здесь тоже???? ---

    RegistrationResponse registrationResponse = new RegistrationResponse();

    String email = request.getEmail();
    Optional<User> userOptional = userRepository.findByEmail(email);

    if (!userOptional.isEmpty()) {
      registrationResponse.setResult(false);
      RegistrationResponseErrors errors = new RegistrationResponseErrors();
      errors.setEmail("Этот e-mail уже зарегистрирован");
      registrationResponse.setErrors(errors);
      return registrationResponse;
    }

    String name = request.getName();

    if (name.matches("\\s+") || name.isEmpty()) {

      registrationResponse.setResult(false);
      RegistrationResponseErrors errors = new RegistrationResponseErrors();
      errors.setName("Имя указано неверно");
      registrationResponse.setErrors(errors);
      return registrationResponse;

    }

    String password = request.getPassword();

    if (password.length() < 6) {

      registrationResponse.setResult(false);
      RegistrationResponseErrors errors = new RegistrationResponseErrors();
      errors.setPassword("Пароль короче 6-ти символов");
      registrationResponse.setErrors(errors);
      return registrationResponse;

    }

    Optional<CaptchaCode> captchaCodeOptional = captchaCodeRepository
        .checkSecret(request.getCaptchaSecret());

    if (captchaCodeOptional.isEmpty()) {
      registrationResponse.setResult(false);
      return registrationResponse;
    }

    CaptchaCode captchaCodeCurrent = captchaCodeOptional.get();

    if (!request.getCaptcha().equals(captchaCodeCurrent.getCode())) {

      registrationResponse.setResult(false);
      RegistrationResponseErrors errors = new RegistrationResponseErrors();
      errors.setCaptcha("Код с картинки введён неверно");
      registrationResponse.setErrors(errors);
      return registrationResponse;

    }

    User newUser = new User();
    newUser.setEmail(email);
    newUser.setPassword(password);
    newUser.setName(name);
    newUser.setModerator(false);
    newUser.setRegTime(Instant.now());
    userRepository.save(newUser);
    registrationResponse.setResult(true);

    return registrationResponse;


  }
}
