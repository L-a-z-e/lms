package com.lms.lms.member.repository;

import com.lms.lms.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepsitory extends JpaRepository<Member, String> {

    Optional<Member> findByEmailAuthKey(String emailAuthkey);
    Optional<Member> findByUserIdAndUserName(String userId, String userName);
    Optional<Member> findByResetPasswordKey(String resetPasswordKey);
}
