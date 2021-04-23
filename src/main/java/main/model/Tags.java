package main.model;

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
@Table(name = "tags")
@Data
public class Tags {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(mappedBy = "tags", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Tag2post> tag2post;


}
