package main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tag2post")
@Data
public class Tag2post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
  private Posts posts;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)
  private Tags tags;

}
