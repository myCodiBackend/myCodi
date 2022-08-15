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
//
//    @PutMapping("/mycodi/comments/{id}")
//    public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest){
//        return commentService.updateComment(id, commentRequestDto, httpServletRequest);
//    }

    @DeleteMapping("/mycodi/comments/{id}")
    public ResponseDto<?> deleteComment(@PathVariable Long id, HttpServletRequest httpServletRequest){
        return commentService.deleteComment(id, httpServletRequest);
    }

}