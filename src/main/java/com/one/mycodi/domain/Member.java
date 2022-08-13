package com.one.mycodi.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String username;

    @Column
    String password;
}
