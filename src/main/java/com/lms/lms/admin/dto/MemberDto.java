package com.lms.lms.admin.dto;


import lombok.Data;

import java.time.LocalDateTime;

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
}
