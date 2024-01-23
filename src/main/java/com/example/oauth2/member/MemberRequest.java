package com.example.oauth2.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequest {
    private String nickname;

    private String profileImage;
}
