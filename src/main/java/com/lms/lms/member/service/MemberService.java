package com.lms.lms.member.service;

import com.lms.lms.admin.dto.MemberDto;
import com.lms.lms.member.entity.Member;
import com.lms.lms.member.model.MemberInput;
import com.lms.lms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput parameter);

    /**
     * *uuid에 해당하는 계정을 활성화함
     */
    boolean emailAuth(String uuid);

    /**
     * 입력한 이메일로 비밀번호 초기화 정보를 전송
     */
    boolean sendResetPassword(ResetPasswordInput parameter);

    /**
     * 입력받은 uuid에 대해서 password로 초기화 함
     * @param id
     * @param password
     * @return
     */
    boolean resetPassword(String id, String password);

    /**
     * 입력받은 uuid값이 유효한지 확인
     * @return
     */
    boolean checkResetPassword(String uuid);

    /**
     * 회원의 목록을 리턴(관리자에서만 사용가능)
     * @return
     */
    List<MemberDto> list();
}
