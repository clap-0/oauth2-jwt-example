package com.example.oauth2.member.domain;

import com.example.oauth2.auth.oauth.OauthProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthInfo {

    private String oauthId;

    @Enumerated(EnumType.STRING)
    private OauthProvider provider;
}
