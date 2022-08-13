package com.one.mycodi.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String imgUrl;

    @Column(nullable = false)
    String content;


    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne
    Member member;

    int heart;


}
