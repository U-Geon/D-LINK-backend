package com.alpha.DLINK.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/api/**", "/graphiql", "/graphql",
            "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
            "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html"
    };

    /**
     * 이 메서드는 정적 자원에 대해 보안 적용 x
     * ex> HTML, CSS
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity
    ) throws Exception {
        httpSecurity
                // stateless한 rest api를 개발할 것이므로 csrf 공격에 대한 옵션은 꺼둔다.
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 특정 URL에 대한 권한 설정.
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()
                        .requestMatchers("/swagger-ui/*") // 특정 url에 대한 인가 요청 허용
                ).httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(login -> login
                        .loginPage("/login") // 로그인 페이지
                        .defaultSuccessUrl("/main") // 성공 시 url
                        .failureUrl("/") // 실패 시 url
                        .permitAll()
                );
                // 1. 먼저, JwtAuthorizationFilter가 실행되어 요청 헤더에서 JWT 토큰을 추출하고 이 토큰을 검증한다.
//                .addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }

    /**
     * "JWT 토큰을 통하여서 사용자를 인증한다." -> 이 메서드는 JWT 인증 필터를 생성한다.
     * JWT 인증 필터는 요청 헤더의 JWT 토큰을 검증하고, 토큰이 유효하면 토큰에서 사용자의 정보와 권한을 추출하여 SecurityContext에 저장한다.
     */
//    @Bean
//    public JwtAuthorizationFilter jwtAuthorizationFilter(CustomUserDetailsService userDetailsService) {
//        return new JwtAuthorizationFilter(userDetailsService);
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}