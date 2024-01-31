package com.example.oauth2.member.service;

import com.example.oauth2.member.domain.Member;
import com.example.oauth2.member.dto.MemberRequest;
import com.example.oauth2.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected member"));
    }

    @Transactional
    public void update(MemberRequest request, Member member) {
        System.out.println("request.getNickname() = " + request.getNickname());
        System.out.println("request.getProfileImage() = " + request.getProfileImage());
        member.update(request.getNickname(), request.getProfileImage());
    }
}
