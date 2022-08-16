package com.one.mycodi.dto.response;


import com.one.mycodi.domain.Comment;
import com.one.mycodi.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostListResponseDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    private List<Comment> commentList;


    public PostListResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getMember().getUsername();
        this.createAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentList = post.getComments();

    }
}
