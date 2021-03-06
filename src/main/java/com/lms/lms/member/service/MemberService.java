package com.lms.lms.member.service;

import com.lms.lms.admin.dto.MemberDto;
import com.lms.lms.admin.model.MemberParam;
import com.lms.lms.admin.model.ServiceResult;
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
    List<MemberDto> list(MemberParam parameter);

    /**
     * 회원 상세정보
     * @param userId
     * @return
     */
    MemberDto detail(String userId);

    /**
     * 회원 상태 변경
     * @param userId
     * @param userStatus
     * @return
     */
    boolean updateStatus(String userId, String userStatus);

    /**
     * 회원 비밀번호 초기화
     * @param userId
     * @param password
     * @return
     */
    boolean updatePassword(String userId, String password);

    /**
     * 회원 정보 페이지 내 비밀번호 변경 기능
     * @param parameter
     * @return
     */
    ServiceResult updateMemberPassword(MemberInput parameter);

    /**
     * 회원 정보 수정
     * @param parameter
     * @return
     */
    ServiceResult updateMemberInfo(MemberInput parameter);
}
