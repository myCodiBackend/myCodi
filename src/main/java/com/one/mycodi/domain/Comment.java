package com.one.mycodi.domain;



import javax.persistence.*;
//리베이스 테스트
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    String content;

}
