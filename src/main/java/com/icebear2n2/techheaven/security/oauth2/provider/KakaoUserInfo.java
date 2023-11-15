package com.icebear2n2.techheaven.security.oauth2.provider;

import com.icebear2n2.techheaven.security.oauth2.OAuth2UserInfo;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;
    private final Map<String, Object> attributesAccount;
    private final Map<String, Object> attributeProfile;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.attributeProfile = (Map<String, Object>) attributesAccount.get("profile");
    }


    @Override
    public String getProviderUserId() {
        return attributes.get("id").toString();
    }


    @Override
    public String getProvider() {
        return "Kakao";
    }

    @Override
    public String getEmail() {
        if (attributesAccount.get("email") == null) {
            return "null";
        }

        return attributesAccount.get("email").toString();
    }

    @Override
    public String getName() {
        return attributeProfile.get("nickname").toString();
    }
}
