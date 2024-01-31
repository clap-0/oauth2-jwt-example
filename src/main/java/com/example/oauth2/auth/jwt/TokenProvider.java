package com.example.oauth2.auth.jwt;

import com.example.oauth2.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), member);
    }

    /**
     * JWT 토큰을 생성하는 메서드이다.
     * @param expiry 토큰의 만료 시간
     * @param member 회원 정보
     * @return 생성된 토큰
     */
    private String makeToken(Date expiry, Member member) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)   // 헤더 typ(타입) : JWT
                .setIssuedAt(now)                               // 내용 iat(발급 일시) : 현재 시간
                .setExpiration(expiry)                          // 내용 exp(만료일시) : expiry 멤버 변수값
                .setSubject(String.valueOf(member.getId()))     // 내용 sub(토큰 제목) : 회원 ID
                .claim("id", member.getId())              // 클레임 id : 회원 ID
                // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    /**
     * JWT 토큰의 유효성을 검증하는 메서드이다.
     * @param token 검증할 JWT 토큰
     * @return 토큰 유효 여부
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())   // 비밀값으로 복호화
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) { // 복호화 과정에서 에러가 나면 유효하지 않은 토큰이다.
            return false;
        }
    }

    /**
     * 토큰 기반으로 인증 정보를 가져오는 메서드이다.
     * @param token 인증된 회원의 토큰
     * @return 인증 정보를 담은 Authentication 객체
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new User(claims.getSubject(), "", authorities), token, authorities);
    }

    /**
     * 토큰에서 회원 ID를 가져오는 메서드이다.
     * @param token JWT 토큰
     * @return 회원 ID
     */
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    /**
     * 주어진 토큰에서 클레임을 조회하는 메서드이다.
     * @param token JWT 토큰
     * @return 클레임 객체
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

}
