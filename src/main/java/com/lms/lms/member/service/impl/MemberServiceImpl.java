package com.lms.lms.member.service.impl;

import com.lms.lms.admin.dto.MemberDto;
import com.lms.lms.admin.mapper.MemberMapper;
import com.lms.lms.admin.model.MemberParam;
import com.lms.lms.admin.model.ServiceResult;
import com.lms.lms.component.MailComponent;
import com.lms.lms.member.entity.Member;
import com.lms.lms.member.exception.MemberEmailAuthException;
import com.lms.lms.member.exception.MemberStopException;
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
                .userStatus(Member.MEMBER_STATUS_REQ)
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
        member.setUserStatus(Member.MEMBER_STATUS_ING);
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
        if (Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())){
            throw new MemberEmailAuthException("이메일 활성화 이후에 로그인해주세요");
        }
        if(Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())){
            throw new MemberStopException("정지된 회원입니다.");
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

    @Override
    public MemberDto detail(String userId) {
        Optional<Member> optionalMember = memberRepsitory.findById(userId);
        if(!optionalMember.isPresent()){
            return null;
        }
        Member member = optionalMember.get();
        MemberDto memberDto = MemberDto.of(member);


        return memberDto;
    }

    @Override
    public boolean updateStatus(String userId, String userStatus) {
        Optional<Member> optionalMember = memberRepsitory.findById(userId);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();
        member.setUserStatus(userStatus);
        memberRepsitory.save(member);

        return true;
    }

    @Override
    public boolean updatePassword(String userId, String password) {
        Optional<Member> optionalMember = memberRepsitory.findById(userId);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();
        String encPassword = BCrypt.hashpw(password,BCrypt.gensalt());
        member.setPassword(encPassword);
        memberRepsitory.save(member);

        return true;
    }

    @Override
    public ServiceResult updateMemberPassword(MemberInput parameter) {

        String userId = parameter.getUserId();
        Optional<Member> optionalMember = memberRepsitory.findById(userId);

        if(!optionalMember.isPresent()){
            return new ServiceResult(false,"회원정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        if(!BCrypt.checkpw(parameter.getPassword(),member.getPassword())){
            return new ServiceResult(false,"비밀번호가 일치하지 않습니다.");
        }

        String encPassword = BCrypt.hashpw(parameter.getNewPassword(),BCrypt.gensalt());
        member.setPassword(encPassword);
        memberRepsitory.save(member);

        return new ServiceResult(true);
    }

    @Override
    public ServiceResult updateMemberInfo(MemberInput parameter) {
        String userId = parameter.getUserId();
        Optional<Member> optionalMember = memberRepsitory.findById(userId);

        if(!optionalMember.isPresent()){
            return new ServiceResult(false,"회원정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();
        member.setPhone(parameter.getPhone());
        memberRepsitory.save(member);

        return new ServiceResult(true);
    }
}
