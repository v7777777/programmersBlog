package main.model;

import java.util.stream.Stream;
import javax.persistence.Column;


public enum GlobalSettingsValues {

  MULTIUSER_MODE("Многопользовательский режим"),
  POST_PREMODERATION( "Премодерация постов"),
  STATISTICS_IS_PUBLIC("Показывать всем статистику блога");

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Column(name = "name", nullable = false)
  private String name; // название имя настройки

  GlobalSettingsValues(String name) {

    this.name = name;
  }

  public static GlobalSettingsValues of(String name) {  // получить перем emum по строке
    return Stream.of(GlobalSettingsValues.values())
        .filter(p -> p.getName() == name)
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }



}



