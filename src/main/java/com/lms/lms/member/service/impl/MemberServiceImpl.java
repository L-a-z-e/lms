package com.lms.lms.member.service.impl;

import com.lms.lms.component.MailComponent;
import com.lms.lms.member.entity.Member;
import com.lms.lms.member.model.MemberInput;
import com.lms.lms.member.repository.MemberRepsitory;
import com.lms.lms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepsitory memberRepsitory;
    private final MailComponent mailComponent;

    @Override
    public boolean register(MemberInput parameter) {


        Optional<Member> optionalMember =memberRepsitory.findById(parameter.getUserId());
        if (optionalMember.isPresent()){

            return false;
        }
        String uuid = UUID.randomUUID().toString();

        Member member = new Member();
        member.setUserId(parameter.getUserId());
        member.setUserName(parameter.getUserName());
        member.setPassword(parameter.getPassword());
        member.setPhone(parameter.getPhone());
        member.setRegisterDate(LocalDateTime.now());
        member.setEmailAuthStatus(false);
        member.setEmailAuthKey(uuid);
        memberRepsitory.save(member);

        String email = parameter.getUserId();
        String subject = "LMS 사이트 가입을 축하드립니다.";
        String text ="<p>LMS 사이트 가입을 축하드립니다.</p><p>링크를 클릭하셔서 가입을 완료하세요.</p>"
                + "<div><a href='http://localhost:8080/member/email-auth?id=" + uuid + "'> 가입 완료 </a></div>";
        mailComponent.sendMail(email,subject,text);

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {
        Optional<Member> optionalMember = memberRepsitory.findByEmailAuthKey(uuid);

        if (!optionalMember.isPresent()){
            return  false;
        }

        Member member = optionalMember.get();
        member.setEmailAuthStatus(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepsitory.save(member);


        return false;
    }
}
