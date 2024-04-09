package com.alpha.DLINK.setting.config;

import com.alpha.DLINK.setting.jwt.filter.TokenExceptionFilter;
import com.alpha.DLINK.setting.oauth2.service.OAuth2MemberService;
import com.alpha.DLINK.setting.oauth2.service.OAuth2SuccessHandler;
import com.alpha.DLINK.setting.jwt.filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2MemberService oAuth2MemberService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    /**
     * 이 메서드는 정적 자원에 대해 보안 적용 x
     * ex> HTML, CSS
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity
    ) throws Exception {
        httpSecurity
                // stateless한 rest api를 개발할 것이므로 csrf 공격에 대한 옵션은 꺼둔다.
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화 -> cookie를 사용하지 않으면 꺼도 된다. (cookie를 사용할 경우 httpOnly(XSS 방어), sameSite(CSRF 방어)로 방어해야 한다.)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 특정 URL에 대한 권한 설정.
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/swagger-ui/*", "/api/*", "/login/oauth2/*").permitAll() // 특정 url에 대한 인가 요청 허용
                        .anyRequest().permitAll()

                )
                // 세션 사용 x
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 로그인 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
                .oauth2Login(login -> login
                        .userInfoEndpoint(userInfoEndpointConfig -> // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정
                                userInfoEndpointConfig.userService(oAuth2MemberService)
                        )
                        .successHandler(oAuth2SuccessHandler) // 로그인 성공 이후 핸들러 처리 로직
                );
                // 1. 먼저, tokenAuthenticationFilter가 실행되어 요청 헤더에서 JWT 토큰을 추출하고 검증
                // jwt 관련 설정
//                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new TokenExceptionFilter(), tokenAuthenticationFilter.getClass()); // 토큰 예외 핸들링
        return httpSecurity.build();
    }



    // cors configuration
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}