package com.lms.lms.member.service;

import com.lms.lms.member.model.MemberInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput parameter);

    /**
     * *uuid에 해당하는 계정을 활성화함
     */
    boolean emailAuth(String uuid);




}
