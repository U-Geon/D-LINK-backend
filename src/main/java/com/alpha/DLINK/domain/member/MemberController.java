package com.alpha.DLINK.domain.member;

import com.alpha.DLINK.domain.member.dto.SignupDto;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.setting.jwt.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    // oauth2 login 이후 생성된 OAuth2User 객체를 사용하기 위해 @AuthenticationPrincipal 사용!
    @PostMapping("/join")
    public ResponseEntity<Object> signUp(@RequestBody SignupDto signupDto) {
        Member member = memberService.findByEmail(signupDto.getEmail());

        memberService.update(member, signupDto.getNickname());

        String accessToken = jwtProvider.createAccessToken(member.getEmail(), member.getId()); // 토큰 생성

        return ResponseEntity.ok()
                .header(AUTHORIZATION, "Bearer" + accessToken)
                .body("{\"msg\" : \"success\"}");
    }
}
