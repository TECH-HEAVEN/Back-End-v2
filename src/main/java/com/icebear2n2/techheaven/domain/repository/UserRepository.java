package com.icebear2n2.techheaven.domain.repository;

import com.icebear2n2.techheaven.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    User findByEmail(String email);

    User findByUsername(String username);

    Optional<User> findByProviderAndProviderUserId(String provider, String providerId);

    void deleteByDeletedAtBefore(Timestamp threshold);

    User findByNickname(String nickname);


}