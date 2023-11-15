package com.icebear2n2.techheaven.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRecoveryRequest {
    private Long userId;
    private String code;
    private String newPassword;
    private String confirmNewPassword;
}