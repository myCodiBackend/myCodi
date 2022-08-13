package com.one.mycodi.controller;


import com.one.mycodi.dto.request.PostRequestDto;
import com.one.mycodi.dto.response.ResponseDto;
import com.one.mycodi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @RequestMapping(value = "/mycodi/posts", method = RequestMethod.POST) // 게시글 작성
    public ResponseDto<?> createPost(@RequestPart(value = "post") PostRequestDto requestDto) throws IOException {
        return postService.createPost(requestDto);
    }
}
