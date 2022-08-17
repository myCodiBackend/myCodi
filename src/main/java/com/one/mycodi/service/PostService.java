package com.one.mycodi.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.one.mycodi.domain.Member;
import com.one.mycodi.domain.Post;
import com.one.mycodi.dto.request.ContentRequestDto;
import com.one.mycodi.dto.request.TitleRequestDto;
import com.one.mycodi.dto.response.PostListResponseDto;
import com.one.mycodi.dto.response.PostResponseDto;
import com.one.mycodi.dto.response.ResponseDto;
import com.one.mycodi.jwt.TokenProvider;
import com.one.mycodi.repository.CommentRepository;
import com.one.mycodi.repository.PostRepository;
import com.one.mycodi.shared.S3Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;
    private final CommentRepository commentRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Transactional
    public ResponseDto<?> createPost(TitleRequestDto titleRequestDto,ContentRequestDto contentRequestDto, MultipartFile multipartFile, HttpServletRequest httpServletRequest) throws IOException {// post 작성

        if (null == httpServletRequest.getHeader("RefreshToken")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == httpServletRequest.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(httpServletRequest);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        String imageUrl = null;

        if (!multipartFile.isEmpty()) {
            String fileName = S3Utils.buildFileName(multipartFile.getOriginalFilename());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            InputStream inputStream = multipartFile.getInputStream();
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));


            imageUrl = amazonS3Client.getUrl(bucketName, fileName).toString();
        }


        Post post = Post.builder()
                .title(titleRequestDto.getTitle())
                .content(contentRequestDto.getContent())
                .imageUrl(imageUrl)
                .member(member)
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
                        .imageUrl(post.getImageUrl())
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
    public ResponseDto<?> getAllPostByPostHeart() {

        List<Post> postList = postRepository.findAllByOrderByHeartCountDesc();
        List<PostListResponseDto> postListResponseDtoArrayList = new ArrayList<>();
        for (Post post : postList) {
            PostListResponseDto postListResponseDto = new PostListResponseDto(post);
            postListResponseDtoArrayList.add(postListResponseDto);
        }

        return ResponseDto.success(postListResponseDtoArrayList);
    }


    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id) { // post 단건 조회
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("200", "존재하지 않는 게시글 id 입니다.");
        }

//        List<Comment> commentList = commentRepository.findAllByPost(post);
//        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
//
//        for(Comment comment : commentList){
//            commentResponseDtoList.add(
//                    CommentResponseDto.builder()
//                    .id(comment.getId())
//                    .author(post.getMember().getUsername())
//                    .content(comment.getContent())
//                    .createdAt(comment.getCreatedAt())
//                    .modifiedAt(comment.getModifiedAt())
//                    .build()
//            );
//        }

        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getMember().getUsername())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .heart(post.getPostHeart().size())
//                        .comments(commentResponseDtoList)
                        .imageUrl(post.getImageUrl())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> updatePost(Long id,TitleRequestDto titleRequestDto,ContentRequestDto contentRequestDto, MultipartFile multipartFile, HttpServletRequest request)throws IOException {   // post 업데이트
        if (null == request.getHeader("RefreshToken")) {
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



        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("200", "존재하지 않는 게시글 id 입니다.");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        String imageUrl;
        if (!multipartFile.isEmpty()) {

            String fileName = S3Utils.buildFileName(multipartFile.getOriginalFilename());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            try( InputStream inputStream = multipartFile.getInputStream() ) {
                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));


                imageUrl = amazonS3Client.getUrl(bucketName, fileName).toString();

            }
            catch (IOException e){
                throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생하였습니다 (%s)", multipartFile.getOriginalFilename()));
            }
            post.updateImage(imageUrl);
            post.update(titleRequestDto,contentRequestDto); // 타이틀과 컨텐트는 무조건 dto내용을 반영하는 코드
        }
        else {

            post.update(titleRequestDto,contentRequestDto);

        }




        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getMember().getUsername())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .imageUrl(post.getImageUrl())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> deletePost(Long id,HttpServletRequest request) { // post 삭제
        if (null == request.getHeader("RefreshToken")) {
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


        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("200", "존재하지 않는 게시글 id 입니다.");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 삭제할 수 있습니다.");
        }


        postRepository.delete(post);

        return ResponseDto.success("delete success");
    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest httpServletRequest) {
        if (!tokenProvider.validateToken(httpServletRequest.getHeader("RefreshToken"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}

