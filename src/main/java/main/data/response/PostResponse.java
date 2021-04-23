package main.data.response;

import lombok.Data;

@Data
public class PostResponse {

  private int id;
  private long timestamp;
  private UserPostResponse user;
  private String title;
  private String announce;
  private int likeCount;
  private int dislikeCount;
  private int commentCount;
  private int viewCount;

}
