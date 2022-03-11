package com.lms.lms.admin.dto;


import com.lms.lms.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDto {
    private String userId;
    private String userName;
    private String password;
    private String phone;
    private LocalDateTime registerDate;


    private boolean emailAuthStatus;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDt;

    private boolean adminYn;

    long totalCount;
    long seq;

    public static MemberDto of(Member member){
       return   MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .password(member.getPassword())
                .phone(member.getPhone())
                .registerDate(member.getRegisterDate())
                .emailAuthStatus(member.isEmailAuthStatus())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())
                .adminYn(member.isAdminYn())
                .build();
    }
}
