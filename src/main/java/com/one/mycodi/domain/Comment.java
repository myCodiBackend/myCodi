package com.one.mycodi.domain;



import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

//코멘트 수정, 리베이스 테스트4

    String content;

}
