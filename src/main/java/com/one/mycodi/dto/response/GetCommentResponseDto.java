package com.one.mycodi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentResponseDto {
    private Long id;
    private String author;
    private String content;
    private String createdAt;
    private String modifiedAt;
}