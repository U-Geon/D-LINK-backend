package com.alpha.DLINK.setting.security.oauth2.service;

import com.alpha.DLINK.domain.member.domain.Member;
import com.alpha.DLINK.domain.member.repository.MemberRepository;
import com.alpha.DLINK.setting.security.oauth2.CustomOauth2User;
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

        // 소셜에서 전달받은 정보를 가진 OAuth2User 에서 Map 을 추출하여 OAuth2Attribute 를 생성
        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.info("access token 값 : {} ", attributes);

        String userNameAttributeName = userRequest // yml에서 설정해준 값 : (id)
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        log.info("userNameAttributeName : {}", userNameAttributeName);

        // DB 로직
        Member member = getMember(attributes);

        return new CustomOauth2User(member, attributes);
    }

    private Member getMember(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = kakaoAccount.get("email").toString();

        Member member = memberRepository.findByEmail(email).orElse(Member.create(email));
        memberRepository.save(member);
        return member;
    }
}
