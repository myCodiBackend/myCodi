package com.one.mycodi.controller;

import com.one.mycodi.dto.request.CommentRequestDto;
import com.one.mycodi.dto.response.ResponseDto;
import com.one.mycodi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/mycodi/comments")
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest) {
        return commentService.createComment(commentRequestDto, httpServletRequest);
    }

    @PutMapping("/mycodi/comments")
    public ResponseDto<?> updateComment(@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest){
        return commentService.updateComment
    }
}