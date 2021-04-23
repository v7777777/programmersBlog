package main.model;

import java.time.Instant;
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
@Table(name = "post_votes")  // like 1 or dislike -1
@Data
public class PostVotes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private Users users;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
  private Posts posts;

  @Column(name = "time", nullable = false)
  private Instant time;

  @Column(name = "value", nullable = false, columnDefinition = "TINYINT")
  private boolean value;

}
