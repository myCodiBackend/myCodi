package com.one.mycodi.controller;


import com.one.mycodi.dto.request.ContentRequestDto;
import com.one.mycodi.dto.request.TitleRequestDto;
import com.one.mycodi.dto.response.ResponseDto;
import com.one.mycodi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @RequestMapping(value = "/api/posts", method = RequestMethod.POST) // 게시글 작성
    public ResponseDto<?> createPost(@RequestPart(value = "title") TitleRequestDto titlerequestDto, @RequestPart(value = "content") ContentRequestDto contentrequestDto, @RequestPart(value = "imageUrl") MultipartFile multipartFile,
                                     HttpServletRequest httpServletRequest) throws IOException {
        return postService.createPost(titlerequestDto,contentrequestDto,multipartFile, httpServletRequest);
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

    @RequestMapping(value = "/api/posts/{id}", method = RequestMethod.PUT) //게시글 수정,이미지까지
    public ResponseDto<?> updatePost(@PathVariable Long id,@RequestPart(value = "title",required = false) TitleRequestDto titlerequestDto, @RequestPart(value = "content",required = false) ContentRequestDto contentrequestDto, @RequestPart(value = "imageUrl",required = false) MultipartFile multipartFile,
                                     HttpServletRequest httpServletRequest)throws IOException {

//        System.out.println("multipart!!! "+ multipartFile.getOriginalFilename());
//
//        boolean fileEmpty = multipartFile.isEmpty();
//        boolean fileNull = multipartFile.getOriginalFilename() == null;
//        boolean fileEN = Objects.equals(multipartFile.getOriginalFilename(), " ");
//
//        System.out.println("fileEN = " + fileEN);
//        System.out.println("fileNull = " + fileNull);
//        System.out.println("fileEmpty = " + fileEmpty);

        return postService.updatePost(id, titlerequestDto,contentrequestDto,multipartFile,httpServletRequest);
    }

    @RequestMapping(value = "/api/posts/{id}", method = RequestMethod.DELETE) // 게시글 삭제
    public ResponseDto<?> deletePost(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return postService.deletePost(id, httpServletRequest);
    }
}