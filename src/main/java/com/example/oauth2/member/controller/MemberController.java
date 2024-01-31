package com.example.oauth2.member.controller;

import com.example.oauth2.member.dto.MemberRequest;
import com.example.oauth2.member.dto.MemberResponse;
import com.example.oauth2.member.service.MemberService;
import com.example.oauth2.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/api/member")
    public MemberResponse getMember(Principal principal) {
        Member member = memberService.findById(Long.parseLong(principal.getName()));
        return MemberResponse.builder()
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .build();
    }

    @PatchMapping("/api/member")
    public void updateMember(@RequestBody MemberRequest request, Principal principal) {
        Member member = memberService.findById(Long.parseLong(principal.getName()));
        memberService.update(request, member);
    }
}
