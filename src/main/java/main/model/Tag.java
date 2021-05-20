package main.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tags")
@Data

public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name", nullable = false)
  private String name;


  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
      name = "tag2post",
      joinColumns = {@JoinColumn(name = "tag_id")},
      inverseJoinColumns = {@JoinColumn(name = "post_id")}
  )
  private List<Post> posts;

  @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Tag2post> tag2posts;


}


