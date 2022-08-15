package com.one.mycodi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
public class Member  extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;


    @Column(nullable = false)
    @JsonIgnore
    private String password;


}
