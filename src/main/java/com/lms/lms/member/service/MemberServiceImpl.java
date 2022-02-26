package com.lms.lms.member.service;

import com.lms.lms.member.entity.Member;
import com.lms.lms.member.model.MemberInput;
import com.lms.lms.member.repository.MemberRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepsitory memberRepsitory;

    @Override
    public boolean register(MemberInput parameter) {

        Member member = new Member();
        member.setUserId(parameter.getUserId());
        member.setUserName(parameter.getUserName());
        member.setPassword(parameter.getPassword());
        member.setPhone(parameter.getPhone());
        member.setRegisterDate(LocalDateTime.now());
        memberRepsitory.save(member);

        return false;
    }
}
