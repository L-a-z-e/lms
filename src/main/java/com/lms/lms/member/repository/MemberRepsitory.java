package com.lms.lms.member.repository;

import com.lms.lms.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepsitory extends JpaRepository<Member, String> {

}
