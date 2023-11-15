package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.entity.Role;
import com.icebear2n2.techheaven.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private boolean success;
    private String message;
    private UserData data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserData {
        private Long userId;
        private String username;
        private String nickname;
        private String email;
        private String provider;
        private String providerUserId;
        private String userPhone;
        private Enum<Role> role;
        private String createdAt;
        private String updatedAt;
        private String deletedAt;

        public UserData(User user) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.provider = user.getProvider();
            this.providerUserId = user.getProviderUserId();
            this.userPhone = user.getUserPhone();
            this.role = user.getRole();
            this.createdAt = user.getCreatedAt().toString();
            this.updatedAt = user.getUpdatedAt().toString();
            this.deletedAt = user.getDeletedAt() != null ? user.getDeletedAt().toString() : null;
        }
    }

    public static UserResponse success(User user) {
        return new UserResponse(true, "Success", new UserData(user));
    }

    public static UserResponse failure(String message) {
        return new UserResponse(false, message, null);
    }
}