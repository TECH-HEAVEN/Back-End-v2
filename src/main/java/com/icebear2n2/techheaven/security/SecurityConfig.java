package com.icebear2n2.techheaven.security;

import com.icebear2n2.techheaven.domain.repository.UserRepository;
import com.icebear2n2.techheaven.security.jwt.JwtAuthorizationFilter;
import com.icebear2n2.techheaven.security.oauth2.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Random;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final Random random;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())

                // 시큐리티 세션 일부 사용 (인증 / 인가)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                // form 로그인 비활성화
                .formLogin(AbstractHttpConfigurer::disable)

                // 기존 http 방식 비활성화 -> Bearer 토큰
                .httpBasic(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(r -> r.anyRequest().permitAll())

                .authorizeHttpRequests(r ->
                        r.requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/users/login")
                                        , AntPathRequestMatcher.antMatcher("/users/signup")
                                        , AntPathRequestMatcher.antMatcher("/token")
                                        , AntPathRequestMatcher.antMatcher("/verification")
                                        , AntPathRequestMatcher.antMatcher("/users/delete")
                                ).permitAll()
//                                .requestMatchers(AntPathRequestMatcher.antMatcher("/users/delete")
////                                        , AntPathRequestMatcher.antMatcher("/verification/balance")
//                                )
//                                .hasAnyRole("ADMIN")
                                .anyRequest().permitAll())

                // 로그인 성공 시, 토큰 받기
                .oauth2Login(oauth ->
                        oauth.userInfoEndpoint((u) -> u.userService(new OAuth2UserService(userRepository)))
                                .loginPage("http://localhost:8080/api/v1/auth/oauth2/authorization/kakao")
                                .defaultSuccessUrl("/token/oauth")
                )
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository))
                .build();

    }
}
