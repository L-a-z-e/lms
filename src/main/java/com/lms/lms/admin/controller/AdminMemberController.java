package com.lms.lms.admin.controller;

import com.lms.lms.admin.dto.MemberDto;
import com.lms.lms.admin.model.MemberInputAdmin;
import com.lms.lms.admin.model.MemberParam;
import com.lms.lms.member.model.MemberInput;
import com.lms.lms.member.service.MemberService;
import com.lms.lms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/admin/member/list")
    public String list(Model model, MemberParam parameter) {
        parameter.init();
        List<MemberDto> members = memberService.list(parameter);


        long totalCount=0;
        if (members != null && members.size()>0){
            totalCount = members.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();

        PageUtil pageUtil = new PageUtil(totalCount, parameter.getPageSize() ,parameter.getPageIndex(), queryString);
        model.addAttribute("list", members);
        model.addAttribute("totalCount", totalCount);
        System.out.println(model.getAttribute("totalCount"));
        model.addAttribute("pager", pageUtil.pager());
        return "admin/member/list";

    }

    @GetMapping("/admin/member/detail")
    public String detail(Model model, MemberParam parameter){
        parameter.init();
        MemberDto member = memberService.detail(parameter.getUserId());
        model.addAttribute("member",member);

        return "admin/member/detail";
    }

    @PostMapping("/admin/member/status")
    public String status(Model model, MemberInputAdmin parameter){

       boolean result = memberService.updateStatus(parameter.getUserId(),parameter.getUserStatus());

       return "redirect:/admin/member/detail?userId=" +parameter.getUserId();
    }

    @PostMapping("/admin/member/password")
    public String password(Model model, MemberInputAdmin parameter){

        boolean result = memberService.updatePassword(parameter.getUserId(),parameter.getPassword());

        return "redirect:/admin/member/detail?userId=" +parameter.getUserId();
    }


}
