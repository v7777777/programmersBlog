package main.model;

import java.time.Instant;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "is_moderator", nullable = false, columnDefinition = "TINYINT")
  private boolean isModerator;

  @Column(name = "reg_time", nullable = false)
  private Instant regTime;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password; // хэш пароля пользователя

  private String code; // код для восстановления пароля

  private String photo;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostVotes> postVotes;  // удалять лайки если человек удалился?? нет тк при удалении запись останется

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostComments> postComments;

}
