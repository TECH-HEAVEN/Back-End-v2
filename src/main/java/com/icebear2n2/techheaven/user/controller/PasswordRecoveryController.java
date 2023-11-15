package com.icebear2n2.techheaven.user.controller;

import com.icebear2n2.techheaven.domain.request.PasswordRecoveryRequest;
import com.icebear2n2.techheaven.user.service.PasswordRecoveryService;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password/recovery")
public class PasswordRecoveryController {
    private final PasswordRecoveryService passwordRecoveryService;

    @PostMapping
    public ResponseEntity<SingleMessageSentResponse> requestCode(@RequestBody Long userId
    ) {
        SingleMessageSentResponse sentResponse = passwordRecoveryService.requestPasswordRecovery(userId);
        return new ResponseEntity<>(sentResponse, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<String> resetPassword(
            @RequestBody
            PasswordRecoveryRequest passwordRecoveryRequest) {
        passwordRecoveryService.verifyAuthCodeAndResetPassword(passwordRecoveryRequest);
        return new ResponseEntity<>("Password reset was successful.", HttpStatus.OK);
    }
}