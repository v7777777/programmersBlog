package main.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "global_settings")
@Data
public class GlobalSettings {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

 // @Enumerated(EnumType.STRING)
 // @Column(name = "code",columnDefinition =
     // "enum('MULTIUSER_MOD','POST_PREMODERATION','STATISTICS_IS_PUBLIC')", nullable = false)

  @Transient // field is not to be serialized
  GlobalSettingsValues globalSettingsValues;

  @Column(name = "code", nullable = false)
  String code;
  @Column(name = "name", nullable = false)
  String name;

  @PostLoad  //@PostLoad — вызывается после загрузки данных сущности из БД.
  void fillTransient() {
    this.globalSettingsValues = GlobalSettingsValues.of(name);
  }

  @PrePersist // @PrePersist — вызывается как только инициирован вызов persist() и исполняется перед остальными действиями.
  void fillPersistent() {
    this.name = globalSettingsValues.getName();
    this.code = globalSettingsValues.toString();
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "value",columnDefinition = "enum('YES','NO')", nullable = false)
  private SettingValue value;
}
