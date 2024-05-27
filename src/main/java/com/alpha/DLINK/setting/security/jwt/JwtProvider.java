package com.alpha.DLINK.setting.security.jwt;

import com.alpha.DLINK.setting.security.oauth2.CustomOauth2User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Component
public class JwtProvider {

    private final Algorithm algorithm;

    @Value("${jwt.access-token.expiration}")
    private Long accessTokenValidTime; // 24시간

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenValidTime; // 2주

    public JwtProvider(@Value("${jwt.secret-key}") String secretKey) {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    public String getUsernameFromAccessToken(String token) {
        return JWT.require(algorithm).build().verify(token).getClaim("username").asString();
    }

    // 토큰 유효 및 만료 확인
    public boolean validate(String token) throws TokenExpiredException {
        try {
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (TokenExpiredException e) {
            throw e;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String generateAccessTokenByNickname(String nickname) {
        return JWT.create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(accessTokenValidTime, ChronoUnit.HOURS))
                .withClaim("username", nickname)
                .sign(algorithm);
    }

    // access 토큰 생성
    public String generateAccessToken(Authentication authentication) {
        CustomOauth2User principal = (CustomOauth2User) authentication.getPrincipal();
        return JWT.create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(accessTokenValidTime, ChronoUnit.HOURS))
                .withClaim("username", principal.getName())
                .sign(algorithm);
    }

    // 액세스 토큰 재발급
    public String reissueAccessToken(String accessToken) {
        String username = getUsernameFromAccessToken(accessToken);
        return generateAccessTokenByNickname(username);
    }

    // JWT 만료 시간 확인
    public boolean isTokenExpired(String token) {
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        return jwt.getExpiresAt().before(new Date());
    }

    // 인증 정보 가져오기
    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities("user");
        User principal = new User(decodedJWT.getClaim("username").asString(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private List<SimpleGrantedAuthority> getAuthorities(String role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
