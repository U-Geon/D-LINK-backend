package com.alpha.DLINK.setting.security.oauth2;

import com.alpha.DLINK.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;


@Getter
@AllArgsConstructor
public class CustomOauth2User implements UserDetails, OAuth2User {

    private Member member; // 사용자의 식별자
    private Map<String, Object> attributes; // 기타 사용자 정보 (예: 이메일, 이름 등)

    public CustomOauth2User(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() { // null일 경우 에러 발생 [principalName cannot be empty]
        return member.getEmail();
    }

    // 사용자 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자 계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자 활성 여부
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 사용자 id 추출
    @Override
    public String getName() {
        return member.getNickname();
    }
}
