package main.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tag2post")
@Getter
@Setter
public class Tag2post {

  // @Id
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private int id;

  @EmbeddedId
  private Tag2postId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
  @MapsId("postId")
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false)
  @MapsId("tagId")
  private Tag tag;

  private Tag2post(){}

  public Tag2post(Post post, Tag tag) {
    this.post = post;
    this.tag = tag;
    id = new Tag2postId(post.getId(), tag.getId());

  }

}
