package com.alpha.DLINK.setting.security.oauth2.service;

import com.alpha.DLINK.domain.member.domain.Member;
import com.alpha.DLINK.setting.security.jwt.JwtProvider;
import com.alpha.DLINK.setting.security.oauth2.CustomOauth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Value("${baseUrl.client-redirect-url}")
    private String baseUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        log.info("success handler 실행");

        if (authentication.getPrincipal() instanceof OAuth2User) {
            CustomOauth2User principal = (CustomOauth2User) authentication.getPrincipal();

            Member member = principal.getMember();
            log.info("member : {} {} ", member.getId(), member.getNickname()); // 수정된 부분

            if (member.getNickname() == null) {
                String redirectionUri = UriComponentsBuilder.fromUriString(baseUrl)
                        .queryParam("email", member.getEmail())
                        .build()
                        .toUriString();
                response.sendRedirect(redirectionUri);

            } else {
                // refresh token을 저장해야 하는데 redis 사용 안하고 그냥
//                String refreshToken = jwtProvider.createRefreshToken(member.getEmail());
//                jwtService.save(new RefreshToken(refreshToken, member.getId()));

                String accessToken = jwtProvider.generateAccessToken(authentication);
                String redirectionUri = UriComponentsBuilder.fromUriString(baseUrl)
                        .queryParam("token", accessToken)
                        .build()
                        .toUriString();
                response.sendRedirect(redirectionUri);
            }
        } else {
            // OAuth2User가 아닌 경우
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}