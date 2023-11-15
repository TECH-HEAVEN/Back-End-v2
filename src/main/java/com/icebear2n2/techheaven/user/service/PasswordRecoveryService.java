package com.icebear2n2.techheaven.user.service;

import com.icebear2n2.techheaven.domain.entity.AuthCode;
import com.icebear2n2.techheaven.domain.entity.User;
import com.icebear2n2.techheaven.domain.repository.UserRepository;
import com.icebear2n2.techheaven.domain.request.CheckAuthCodeRequest;
import com.icebear2n2.techheaven.domain.request.PasswordRecoveryRequest;
import com.icebear2n2.techheaven.domain.request.PhoneRequest;
import com.icebear2n2.techheaven.exception.TechHeavenException;
import com.icebear2n2.techheaven.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordRecoveryService {
    private final UserRepository userRepository;
    private final AuthCodeService authCodeService;


    public SingleMessageSentResponse requestPasswordRecovery(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new TechHeavenException(ErrorCode.USER_NOT_FOUND));
        // 인증 코드 SMS 전송
        return authCodeService.sendAuthCode(new PhoneRequest(userId, user.getUserPhone()));
    }

    public void verifyAuthCodeAndResetPassword(PasswordRecoveryRequest passwordRecoveryRequest) {
        User user = userRepository.findById(passwordRecoveryRequest.getUserId())
                .orElseThrow(() -> new TechHeavenException(ErrorCode.USER_NOT_FOUND));

        // Step 1: 인증코드가 일치하는지 확인하고 해당 인증코드 객체 반환 받기
        CheckAuthCodeRequest checkAuthCodeRequest = new CheckAuthCodeRequest(
                user.getUserId(), user.getUserPhone(), passwordRecoveryRequest.getCode());
        AuthCode validAuthCode = authCodeService.getValidAuthCode(checkAuthCodeRequest.getPhone(), checkAuthCodeRequest.getCode());

        // Step 2: 비밀번호가 일치하는지 확인
        confirmNewPassword(passwordRecoveryRequest.getNewPassword(), passwordRecoveryRequest.getConfirmNewPassword());

        // Step 3: 비밀번호 확인 메서드가 통과하면 인증코드 완료 필드 업데이트
        authCodeService.completedSaveAuthCode(validAuthCode);

        // Step 4: 비밀번호 재설정
        resetPassword(user, passwordRecoveryRequest.getNewPassword());
    }


    private void resetPassword(User user, String newPassword) {
        String encodedPassword = new BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    private void confirmNewPassword(String newPassword, String confirmNewPassword) {
        if (!confirmNewPassword.equals(newPassword)) {
            throw new TechHeavenException(ErrorCode.INVALID_PASSWORD);
        }
    }
}
