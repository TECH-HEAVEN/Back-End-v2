package com.icebear2n2.techheaven.security.oauth2;

import com.icebear2n2.techheaven.domain.entity.Role;
import com.icebear2n2.techheaven.domain.entity.User;
import com.icebear2n2.techheaven.domain.repository.UserRepository;
import com.icebear2n2.techheaven.security.oauth2.provider.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        return proccessOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User proccessOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // attribute 을 파싱해서 공통 객체로 묶는다. -> 관리가 편하다.
        OAuth2UserInfo oAuth2UserInfo;

        super.loadUser(userRequest);
        oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());

        // 중복 체크
        Optional<User> userOptional =
                userRepository.findByProviderAndProviderUserId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderUserId());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();

            // 유저가 존재한다면, update
            user.setEmail(oAuth2UserInfo.getEmail());
            userRepository.save(user);
        } else {
            // token 에 담을 유저 정보
            // 유저의 패스워드가 null 이기에 OAuth 유저는 일반적인 로그인을 할 수 없다.
            user = User.builder()
                    .username(oAuth2UserInfo.getName())
                    .nickname(generateNickname())
                    .email(oAuth2UserInfo.getEmail())
                    .role(Role.ROLE_KAKAO_USER)
                    .provider(oAuth2UserInfo.getProvider())
                    .providerUserId(oAuth2UserInfo.getProviderUserId())
                    .build();
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

    private String generateNickname() {
        Random random = new Random();
        int nickname;
        do {
            nickname = random.nextInt(900000) + 100000;
        } while (userRepository.findByNickname(String.valueOf(nickname)) != null);
        return String.valueOf(nickname);
    }
}
