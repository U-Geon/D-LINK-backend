package com.alpha.DLINK.service.oauth2;

import com.alpha.DLINK.entity.Member;
import com.alpha.DLINK.repository.MemberRepository;
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

        String userNameAttributeName = userRequest // access token으로 받아온 user 정보
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = ((Map<String, Object>) attributes.get("kakao_account")).get("email").toString();


        // DB logic
        if(memberRepository.findByEmail(email).isEmpty()) {
            log.info("회원 가입 실행");
            Member member = Member.create(email);
            memberRepository.save(member);
        }

        return super.loadUser(userRequest);
    }

}
