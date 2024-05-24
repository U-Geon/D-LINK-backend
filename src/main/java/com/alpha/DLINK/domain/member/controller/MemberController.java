package com.alpha.DLINK.domain.member.controller;

import com.alpha.DLINK.domain.member.dto.UpdateDTO;
import com.alpha.DLINK.domain.member.service.MemberService;
import com.alpha.DLINK.domain.member.dto.SignupDTO;
import com.alpha.DLINK.domain.member.domain.Member;
import com.alpha.DLINK.setting.security.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    // oauth2 login 이후 생성된 OAuth2User 객체를 사용하기 위해 @AuthenticationPrincipal 사용!
    @PostMapping("/join")
    @Operation(summary = "회원 가입", description = "이메일, 닉네임 정보를 받아 회원가입 함")
    public ResponseEntity<Object> signUp(@Parameter(required = true, description = "닉네임, 이메일")
                                             @RequestBody @Valid SignupDTO signupDto) {
        try {
            Member member = memberService.findByEmail(signupDto.getEmail());

            memberService.update(member, signupDto.getNickname());

            String accessToken = jwtProvider.generateAccessTokenByNickname(signupDto.getNickname()); // 토큰 생성

            return ResponseEntity.ok()
                    .header(AUTHORIZATION, "Bearer " + accessToken)
                    .body("{\"msg\" : \"success\"}");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/update")
    @Operation(summary = "프로필 수정", description = "닉네임 수정하기")
    public void updateNickname(@AuthenticationPrincipal Member member,
                               @RequestBody UpdateDTO updateDTO) {
        memberService.updateNickname(member.getId(), updateDTO.getNickname());
    }
}