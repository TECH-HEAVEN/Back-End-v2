package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.Role;
import com.icebear2n2.techheaven.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String email;
    private String password;

    public User toEntity(String username, String email, String password) {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(Role.ROLE_USER)
                .build();
    }
}