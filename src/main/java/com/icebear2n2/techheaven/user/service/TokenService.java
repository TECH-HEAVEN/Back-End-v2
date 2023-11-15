package com.icebear2n2.techheaven.user.service;

import com.icebear2n2.techheaven.domain.entity.User;
import com.icebear2n2.techheaven.domain.repository.UserRepository;
import com.icebear2n2.techheaven.exception.TechHeavenException;
import com.icebear2n2.techheaven.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);
    private final UserRepository userRepository;
    @Value("${jwt.secret}")
    private String secretKey;

    public String makeAccessToken(User user) {
        SecretKeySpec keySpec = getKey();

        return Jwts.builder()
                .claim("userId", user.getUserId())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setExpiration(new Date(System.currentTimeMillis() + 120_000000))
                .signWith(keySpec)
                .compact();
    }

    public String makeRefreshToken(User user) {
        SecretKeySpec keySpec = getKey();

        String compact = Jwts.builder()
                .claim("userId", user.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + 800_000000))
                .signWith(keySpec)
                .compact();

        LOGGER.info("compact: " + compact);
        return compact;
    }

    public Map<String, String> generateAndSaveTokens(User user) {
        try {
            String accessToken = makeAccessToken(user);

            String refreshToken = makeRefreshToken(user);

            user.setRefreshToken(refreshToken);

            userRepository.save(user);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

            return tokens;
        } catch (Exception e) {
            throw new TechHeavenException(ErrorCode.FAILED_CREATE_TOKEN);
        }
    }


    public Map<String, Object> getClaims(String token) {
        return (Claims) Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parse(token)
                .getBody();
    }

    private SecretKeySpec getKey() {
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        return new SecretKeySpec(secretKey.getBytes(), hs256.getJcaName());
    }


}
