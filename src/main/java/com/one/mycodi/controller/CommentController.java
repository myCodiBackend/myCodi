package com.one.mycodi.controller;

import com.one.mycodi.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class CommentController {

    @PostMapping("/mycodi/comments")
    public ResponseDto<?> createPost(@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest) {
        return commentService.createComment(commentRequestDto, httpServletRequest)
    }
}