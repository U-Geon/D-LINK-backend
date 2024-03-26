package com.alpha.DLINK.setting.oauth2.service;

import com.alpha.DLINK.member.domain.Member;
import com.alpha.DLINK.setting.oauth2.dto.PrincipalDetails;
import com.alpha.DLINK.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * loadUser 메서드가 실행될 시점엔 이미 Access Token이 정상적으로 발급된 상태이며
 * super.loadUser 메서드를 통해
 * Access Token으로 User 정보를 조회해 옵니다.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.info("access token 값 : ", attributes);

        String userNameAttributeName = userRequest // access token으로 받아온 kakaoId
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();


        // DB 로직
        Member member = null;

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount != null && kakaoAccount.containsKey("email")) {
            String email = kakaoAccount.get("email").toString();

            log.info("회원 가입 실행");
            member = Member.create(email);
            memberRepository.save(member);
        } else {
            log.error("kakao_account에서 이메일을 찾을 수 없습니다.");
        }

        return new PrincipalDetails(member, attributes, userNameAttributeName);
    }

}
