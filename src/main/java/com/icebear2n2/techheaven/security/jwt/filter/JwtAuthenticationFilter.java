package com.icebear2n2.techheaven.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icebear2n2.techheaven.security.oauth2.PrincipalDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        PrincipalDetails loginRequestDto = null;
        try {
            loginRequestDto = objectMapper.readValue(request.getInputStream(), PrincipalDetails.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 유저 이름. 패스워드 토큰 생성
        assert loginRequestDto != null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword()
        );

        LOGGER.info("JwtAuthenticationFilter : COMPLETE CREATE TOKEN!!");

        // authenticate() 함수가 호출되면 인증 provider 가 UserDetails 서비스의
        // loadUserByUsername(토큰의 첫 번째 파라미터) 를 호출하고,
        // UserDetails 를 return 받아서 토큰의 두 번째 파라미터(Credential) 과
        // UserDetails(DB 값)의 getPassword() 함수로 비교하여 일치할 시,
        // Authentication 객체를 만들어서 FilterChain 으로 return 해준다.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        authentication.getPrincipal();
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        Key secretKey = new SecretKeySpec(jwtSecretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        // JWT 생성

        String compact = Jwts.builder()
                .claim("userId", principalDetails.getUser().getUserId())
                .claim("username", principalDetails.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 120_000))
                .signWith(secretKey)
                .compact();

        response.addHeader("X-Auth-Token", "Bearer " + compact);

        logger.info("SUCCESS CREATE JWT TOKEN : " + compact);
    }
}
