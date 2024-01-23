package com.example.oauth2.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String profileImage;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Hobby> hobbyList = new ArrayList<>();

    @Embedded
    private Oauth oauth;

    private String accessToken;

    public Member update(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public Member update(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        return this;
    }
}
