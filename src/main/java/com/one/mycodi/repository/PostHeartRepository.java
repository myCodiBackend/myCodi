package com.one.mycodi.repository;

import com.one.mycodi.domain.Member;
import com.one.mycodi.domain.Post;
import com.one.mycodi.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {

    Optional<PostHeart> findByPostAndMember(Post post, Member member);
}
