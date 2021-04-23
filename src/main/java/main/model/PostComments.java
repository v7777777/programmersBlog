package main.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "post_comments")
@Data
public class PostComments {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id", referencedColumnName = "id")
  private PostComments postComments;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
  private Posts posts;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = false)
  private Users users;

  @Column(name = "time", nullable = false)
  private Instant time;

  @Column(name = "text", nullable = false)
  private String text;

}
