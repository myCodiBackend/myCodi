package com.one.mycodi.controller;


import com.one.mycodi.dto.request.PostRequestDto;
import com.one.mycodi.dto.response.ResponseDto;
import com.one.mycodi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @RequestMapping(value = "/api/posts", method = RequestMethod.POST) // 게시글 작성
    public ResponseDto<?> createPost(@RequestPart(value = "post") PostRequestDto requestDto,@RequestPart(value = "image") MultipartFile multipartFile,
                                     HttpServletRequest httpServletRequest) throws IOException {
        return postService.createPost(requestDto,multipartFile, httpServletRequest);
    }

    @RequestMapping(value = "/api/posts", method = RequestMethod.GET) // 게시글 전체 조회
    public ResponseDto<?> getAllPosts() {
        return postService.getAllPost();
    }

    @GetMapping("/api/posthearts")
    public ResponseDto<?> getAllPostByPostHeart() {
        return postService.getAllPostByPostHeart();
    }

    @RequestMapping(value = "/api/posts/{id}", method = RequestMethod.GET) //게시글 단건 조회
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @RequestMapping(value = "/api/posts/{id}", method = RequestMethod.PATCH) //게시글 수정,이미지까지
    public ResponseDto<?> updatePost(@PathVariable Long id,@RequestPart(value = "post",required = false) PostRequestDto postrequestDto,@RequestPart(value = "image",required = false) MultipartFile multipartFile,
                                     HttpServletRequest httpServletRequest)throws IOException {
        return postService.updatePost(id, postrequestDto,multipartFile,httpServletRequest);
    }

    @RequestMapping(value = "/api/posts/{id}", method = RequestMethod.DELETE) // 게시글 삭제
    public ResponseDto<?> deletePost(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return postService.deletePost(id, httpServletRequest);
    }
}