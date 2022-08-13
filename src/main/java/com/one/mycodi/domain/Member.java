package com.one.mycodi.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Member  extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username;


    @Column
    private String password;


}
