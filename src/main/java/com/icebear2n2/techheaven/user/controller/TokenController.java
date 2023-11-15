package com.icebear2n2.techheaven.user.controller;


import com.icebear2n2.techheaven.security.oauth2.PrincipalDetails;
import com.icebear2n2.techheaven.user.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, String>> getTokenForRegularUser(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Map<String, String> tokens = tokenService.generateAndSaveTokens(principalDetails.getUser());
        LOGGER.info("GENERATED TOKEN FOR USER: {}", principalDetails.getUsername());
        return ResponseEntity.ok(tokens);
    }

    @PreAuthorize("hasRole('ROLE_KAKAO_USER')")
    @GetMapping("/oauth")
    public ResponseEntity<Map<String, String>> getTokenForOAuthUser(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Map<String, String> tokens = tokenService.generateAndSaveTokens(principalDetails.getUser());
        LOGGER.info("GENERATED TOKEN FOR  OAUTH USER: {}", principalDetails.getUsername());
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getToken(@RequestHeader("X-Auth-Token") String token) {
        Map<String, Object> claims = tokenService.getClaims(token.replace("Bearer ", ""));
        if (claims == null || claims.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        LOGGER.info("claims: " + claims);
        return ResponseEntity.ok(claims);
    }
}