package main.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class Tag2postId implements Serializable {

  @Column(name = "post_id")
  private Integer postId;

  @Column(name = "tag_id")
  private Integer tagId;

  public Tag2postId() {

  }

  public Tag2postId(Integer postId, Integer tagId) {
    this.postId = postId;
    this.tagId = tagId;
  }

}
