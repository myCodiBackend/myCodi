package com.one.mycodi.controller;

import com.one.mycodi.domain.PostHeart;
import com.one.mycodi.dto.response.ResponseDto;
import com.one.mycodi.service.PostHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PostHeartController {

    private final PostHeartService postHeartService;

    @PostMapping("/mycodi/like/posts/{id}")
    public ResponseDto<?> postHeart(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return postHeartService.createPostHeart(id, httpServletRequest);
    }

    @DeleteMapping("/mycodi/like/posts/{id}")
    public ResponseDto<?> postHeartDelete(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return postHeartService.deletePostHeart(id, httpServletRequest);
    }
}
