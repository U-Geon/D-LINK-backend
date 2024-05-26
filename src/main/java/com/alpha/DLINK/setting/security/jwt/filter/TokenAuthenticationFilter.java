package com.alpha.DLINK.setting.security.jwt.filter;

import com.alpha.DLINK.setting.security.jwt.JwtProvider;
import com.alpha.DLINK.setting.security.oauth2.service.LoadUserService;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final String TOKEN_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;
    private final LoadUserService loadUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveToken(request);
        log.info("authentication filter 작동.");
        log.info("Access token: {}", accessToken);

        if (accessToken != null) {
            try {
                if (jwtProvider.validate(accessToken)) {
                    String username = jwtProvider.getUsernameFromAccessToken(accessToken);
                    verifyAndSaveAuthentication(request, username);
                }
            } catch (TokenExpiredException e) {
                String s = jwtProvider.reissueAccessToken(accessToken);
                verifyAndSaveAuthentication(request, s);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void verifyAndSaveAuthentication(HttpServletRequest request, String username) {

        UserDetails userDetails = loadUserService.loadUserByUsername(username);
        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}