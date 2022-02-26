package com.lms.lms.member.service;

import com.lms.lms.member.model.MemberInput;

public interface MemberService {

    boolean register(MemberInput parameter);

    /**
     * *uuid에 해당하는 계정을 활성화함
     */
    boolean emailAuth(String uuid);


}
