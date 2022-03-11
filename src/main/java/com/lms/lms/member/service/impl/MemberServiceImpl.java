package com.lms.lms.member.service.impl;

import com.lms.lms.admin.dto.MemberDto;
import com.lms.lms.admin.mapper.MemberMapper;
import com.lms.lms.admin.model.MemberParam;
import com.lms.lms.component.MailComponent;
import com.lms.lms.member.entity.Member;
import com.lms.lms.member.exception.MemberEmailAuthException;
import com.lms.lms.member.model.MemberInput;
import com.lms.lms.member.model.ResetPasswordInput;
import com.lms.lms.member.repository.MemberRepsitory;
import com.lms.lms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepsitory memberRepsitory;
    private final MailComponent mailComponent;
    private final MemberMapper memberMapper;

    @Override
    public boolean register(MemberInput parameter) {


        Optional<Member> optionalMember =memberRepsitory.findById(parameter.getUserId());
        if (optionalMember.isPresent()){

            return false;
        }

        String encryptPassword = BCrypt.hashpw(parameter.getPassword(),BCrypt.gensalt());
        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encryptPassword)
                .registerDate(LocalDateTime.now())
                .emailAuthStatus(false)
                .emailAuthKey(uuid)
                .build();

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
        if(member.isEmailAuthStatus()){
            return false;
        }
        member.setEmailAuthStatus(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepsitory.save(member);


        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepsitory.findById(username);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();
        if (!member.isEmailAuthStatus()){
            throw new MemberEmailAuthException("이메일 활성화 이후에 로그인해주세요");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(member.isAdminYn()){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return new User(member.getUserId(),member.getPassword(),grantedAuthorities);
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {
        Optional<Member> optionalMember = memberRepsitory.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();
        String uuid = UUID.randomUUID().toString();
        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
        memberRepsitory.save(member);

        String email = parameter.getUserId();
        String subject = "[LMS] 비밀번호 초기화 메일";
        String text ="<p>LMS 비밀번호 초기화 메일입니다.</p><p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요.</p>"
                + "<div><a href='http://localhost:8080/member/reset/password?id=" + uuid + "'> 비밀번호 초기화 링크 </a></div>";
        mailComponent.sendMail(email,subject,text);
        System.out.println(mailComponent);

        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {
        Optional<Member> optionalMember = memberRepsitory.findByResetPasswordKey(uuid);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();
        if(member.getResetPasswordLimitDt()==null){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
       if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
           throw new RuntimeException("유효한 날짜가 아닙니다.");
       }

        String encPassword = BCrypt.hashpw(password,BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepsitory.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Member> optionalMember = memberRepsitory.findByResetPasswordKey(uuid);
        if (!optionalMember.isPresent()) {
           return false;
        }
        Member member = optionalMember.get();
        if(member.getResetPasswordLimitDt()==null){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        return true;

    }

    @Override
    public List<MemberDto> list(MemberParam parameter) {

        long totalCount = memberMapper.selectListCount(parameter);
        List<MemberDto> list = memberMapper.selectList(parameter);
        if(!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(MemberDto member : list){
                member.setTotalCount(totalCount);
                member.setSeq(totalCount - parameter.getpageStart() - i);
                i++;
            }
        }
        return list;
        //return memberRepsitory.findAll();

    }
}
