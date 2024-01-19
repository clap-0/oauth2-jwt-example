package com.example.oauth2.auth.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class OauthController {
    private final OauthService oauthService;

    @PostMapping("/api/auth/kakao")
    public ResponseEntity<Void> kakaoLogin(@RequestBody LoginRequest request, HttpServletResponse response) {
        oauthService.kakaoLogin(request.getCode(), response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
