package com.one.mycodi.service;

import com.one.mycodi.domain.Member;
import com.one.mycodi.domain.Post;
import com.one.mycodi.domain.PostHeart;
import com.one.mycodi.dto.response.ResponseDto;
import com.one.mycodi.jwt.TokenProvider;
import com.one.mycodi.repository.PostHeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostHeartService {

    private final TokenProvider tokenProvider;
    private final PostService postService;
    private final PostHeartRepository postHeartRepository;

    @Transactional
    public ResponseDto<?> createPostHeart(Long id, HttpServletRequest request) {

        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);

        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(id);

        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }
        //게시글 좋아요한 사람 0

        Optional<PostHeart> postHeartOptional = postHeartRepository.findByPostAndMember(post, member);
        if (postHeartOptional.isPresent()) {
            return ResponseDto.fail("BAD_REQUEST", "이미 좋아요를 누르셨습니다.");
        }

        if (!postHeartOptional.isPresent()) {
//            post.update(post.getHeartCount());
            PostHeart postHeart = PostHeart.builder()
                    .postHeart(1)
                    .member(member)
                    .post(post)
                    .build();
            postHeartRepository.save(postHeart);
            List<PostHeart> postHearts = postHeartRepository.findByPost(post);
            post.update(postHearts);

        }
        return ResponseDto.success("좋아요");
    }

    @Transactional
    public ResponseDto<?> deletePostHeart(Long id, HttpServletRequest request) {

        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);

        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(id);

        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }
        //게시글 좋아요한 사람 0

        Optional<PostHeart> postHeartOptional = postHeartRepository.findByPostAndMember(post, member);
        if (!postHeartOptional.isPresent()) {
            return ResponseDto.fail("BAD_REQUEST", "좋아요를 누르지 않으셨습니다.");
        }
        postHeartRepository.delete(postHeartOptional.get());
        List<PostHeart> postHearts = postHeartRepository.findByPost(post);
        post.update(postHearts);
        return ResponseDto.success("좋아요 취소");
    }
    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
