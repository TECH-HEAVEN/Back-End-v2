package com.icebear2n2.techheaven.security.oauth2;

// OAuth 제공자 별로 속성명이 일치하지 않음으로 속성 내용 통일
public interface OAuth2UserInfo {
    String getProviderUserId();
    String getProvider();
    String getEmail();
    String getName();
}
