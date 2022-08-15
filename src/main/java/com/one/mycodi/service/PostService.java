package com.one.mycodi.service;


import com.one.mycodi.domain.Post;
import com.one.mycodi.dto.request.PostRequestDto;
import com.one.mycodi.dto.response.PostListResponseDto;
import com.one.mycodi.dto.response.PostResponseDto;
import com.one.mycodi.dto.response.ResponseDto;
import com.one.mycodi.repository.PostRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto) throws IOException {// post 작성

        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
        postRepository.save(post);

        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getMember().getUsername())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .imageUrl(null)
                        .build());


    }


    @Transactional(readOnly = true)
    public ResponseDto<?> getAllPost() {

        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostListResponseDto> dtoList = new ArrayList<>();
        for (Post post : postList) {

            PostListResponseDto postListResponseDto = new PostListResponseDto(post);
            // 결과 저장 리스트에 담기
            dtoList.add(postListResponseDto);
        }



        return ResponseDto.success(dtoList);
    }



    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id) { // post 단건 조회
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("200", "존재하지 않는 게시글 id 입니다.");
        }


        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getMember().getUsername())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .imageUrl(null)
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> updatePost(Long id, PostRequestDto requestDto) {   // post 업데이트

        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("200", "존재하지 않는 게시글 id 입니다.");
        }
        post.update(requestDto);

        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getMember().getUsername())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .imageUrl(null)
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> deletePost(Long id) { // post 삭제
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("200", "존재하지 않는 게시글 id 입니다.");
        }

        postRepository.delete(post);

        return ResponseDto.success("delete success");
    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }


}

