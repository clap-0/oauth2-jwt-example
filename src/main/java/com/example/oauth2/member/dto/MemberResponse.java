package com.example.oauth2.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponse {
    private String nickname;

    private String profileImage;
}
