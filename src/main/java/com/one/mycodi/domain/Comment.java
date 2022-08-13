package com.one.mycodi.domain;


import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;


    @Column
    private String content;

}
