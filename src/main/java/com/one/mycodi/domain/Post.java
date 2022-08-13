package com.one.mycodi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String title;

    @Column
    String imgUrl;

    @Column(nullable = false)
    String content;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne
    Member member;

    int heart;


}
