package com.example.oauth2.global.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * 스프링 시큐리티 기능 비활성화
     */
    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                // 정적 리소스에 대한 스프링 시큐리티 사용을 비활성화
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * 특정 HTTP 요청에 대한 웹 기반 보안 구성
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                // 토큰 기반 인증을 사용하기 때문에 세션 기능 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // 인증, 인가 설정
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}
