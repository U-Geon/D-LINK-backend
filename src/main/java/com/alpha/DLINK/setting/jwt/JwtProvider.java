package com.alpha.DLINK.setting.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKey; // 비밀키

    @Value("${jwt.access-token.expiration}")
    private Long accessTokenValidTime; // 30분

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenValidTime; // 2주

    // 회원 정보 조회

    public String getEmail(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").toString();
    }

    // 토큰 유효 및 만료 확인
    public boolean isExpired(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token); // 토큰의 만료를 검사한다. 만료되었다면 예외를 발생시킴.
            return false; // 예외가 발생하지 않았다면 토큰은 유효하다.
        } catch (Exception e) {
            return true; // 예외 발생 시 토큰은 만료되었거나 유효하지 않음을 의미한다.
        }
    }

    // refresh 토큰 확인
    public boolean isRefreshToken(String token) {
        return getTokenType(token).equals("refresh");
    }

    // access 토큰 확인
    public boolean isAccessToken(String token) {
        return getTokenType(token).equals("access");
    }

    private String getTokenType(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("type").asString();
    }

    // access 토큰 생성
    public String createAccessToken(String email) {
        return createJwt(email, "access", accessTokenValidTime);
    }

    // refresh 토큰 생성
    public String createRefreshToken(String email) {
        return createJwt(email, "refresh", refreshTokenValidTime);
    }

    private String createJwt(String email, String type, Long tokenValidTime) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withClaim("email", email)
                .withClaim("type", type)
                .withIssuedAt(Date.from(Instant.now()))
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenValidTime))
                .sign(algorithm);
    }

    // 액세스 토큰 재발급
    public String reissueAccessToken(String accessToken) {
        if(isExpired(accessToken) || !getTokenType(accessToken).equals("refresh")) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }
        String email = getEmail(accessToken);
        return createAccessToken(email);
    }

    // 리프레시 토큰 재발급 (필요한 경우)
    public String reissueRefreshToken(String refreshToken) {
        if(isExpired(refreshToken) || !getTokenType(refreshToken).equals("refresh")) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }
        String email = getEmail(refreshToken);
        return createRefreshToken(email);
    }

    public Authentication getAuthentication(String accessToken) {

        return null;
    }
}
