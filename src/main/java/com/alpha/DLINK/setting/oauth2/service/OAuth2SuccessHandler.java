package com.alpha.DLINK.setting.oauth2.service;

import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.setting.jwt.JwtProvider;
import com.alpha.DLINK.setting.oauth2.domain.CustomOauth2User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        log.info("success handler 실행");

        if (authentication.getPrincipal() instanceof OAuth2User) {
            CustomOauth2User principal = (CustomOauth2User) authentication.getPrincipal();
            Member member = principal.getMember();
            log.info("member : {}", member.getNickname()); // 수정된 부분

            // jwt 토큰 생성 후 헤더에 담아주기.
            String accessToken = jwtProvider.createAccessToken(member.getEmail());
            response.addHeader("Authorization", "Bearer " + accessToken);

            if (member.getNickname() == null) {
                // JSON 형태로 변환하여 response body에 쓰기 -> tokenfilter에서 이미 null처리가 되어버림.
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("email", member.getEmail());
                responseBody.put("msg", "회원 가입 필요");

                response.setStatus(HttpStatus.OK.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(response.getWriter(), responseBody);
            } else {

                // refresh token을 저장해야 하는데...
//                String refreshToken = jwtProvider.createRefreshToken(member.getEmail());

//                jwtService.save(new RefreshToken(refreshToken, member.getId()));

                // 응답 본문에 메시지 추가
                response.setStatus(HttpStatus.OK.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                Map<String, String> responseBody = new HashMap<>();

                responseBody.put("msg", "로그인 성공");
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(response.getWriter(), responseBody);
            }
        } else {
            // OAuth2User가 아닌 경우
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}