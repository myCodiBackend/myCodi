package com.one.mycodi.domain;

import com.one.mycodi.dto.request.PostRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
    private List<PostHeart> PostHeart = new ArrayList<>();

    @Column
    private int heartCount = this.PostHeart.size();


    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }
    public void updateImage(String imageUrl){
        this.imageUrl = imageUrl;
    }
    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }

    public void update(List<PostHeart> postHearts){
        this.heartCount = postHearts.size();
    }

    public void update(List<PostHeart> postHearts){
        this.heartCount = postHearts.size();
    }


}


