package com.one.mycodi.repository;

import com.one.mycodi.domain.Comment;
import com.one.mycodi.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);
}
