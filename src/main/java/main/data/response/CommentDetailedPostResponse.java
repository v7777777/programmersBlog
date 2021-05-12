package main.data.response;

import lombok.Data;

@Data
public class CommentDetailedPostResponse {

 private int id;
 private long timestamp;
 private String text;
 private UserDetailedPostCommentResponse user;

}
