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
    private Long accessTokenValidTime; // 30분

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenValidTime; // 2주

    public JwtProvider(@Value("${jwt.secret-key}") String secretKey) {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    // 회원 정보 조회
    public String getEmailFromAccessToken(String token) {
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        return jwt.getClaim("email").toString();
    }

    public String getIdFromAccessToken(String token) {
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        return jwt.getClaim("member_id").toString();
    }

    public String getUsernameFromAccessToken(String token) {

        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getClaim("username")
                .asString();
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

//    public String generateRefreshToken(Authentication authentication) {
//
//        User userPrincipal = (User) authentication.getPrincipal();
//
//        String token = JWT.create()
//                .withIssuedAt(Instant.now())
//                .withExpiresAt(Instant.now().plus(refreshTokenExpirationsHour, ChronoUnit.SECONDS))
//                .sign(algorithm);
//
//        redisTemplate.opsForValue().set(token, userPrincipal.getId(), refreshTokenExpirationsHour, TimeUnit.HOURS);
//
//        return token;
//    }

    // 액세스 토큰 재발급
    public String reissueAccessToken(String accessToken) {
        String username = getUsernameFromAccessToken(accessToken);
        return generateAccessTokenByNickname(username);
    }

    // 리프레시 토큰 재발급 (필요한 경우)
//    public String reissueRefreshToken(String refreshToken) {
//        if(validate(refreshToken) || !getTokenType(refreshToken).equals("refresh")) {
//            throw new IllegalArgumentException("Invalid or expired refresh token");
//        }
//        String email = getEmailFromAccessToken(refreshToken);
//        Long id = Long.parseLong(getIdFromAccessToken(refreshToken));
//        return generateRefreshToken(email, id);
//    }

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
