package com.lms.lms.admin;

import com.lms.lms.member.entity.Member;
import com.lms.lms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/admin/member/list")
    public String list(Model model) {
        List<Member> members = memberService.list();
        model.addAttribute("list",members);

        return "admin/member/list";

    }
}
