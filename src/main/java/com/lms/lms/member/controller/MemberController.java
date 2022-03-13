package com.lms.lms.member.controller;

import com.lms.lms.admin.dto.MemberDto;
import com.lms.lms.admin.model.ServiceResult;
import com.lms.lms.member.model.MemberInput;
import com.lms.lms.member.model.ResetPasswordInput;
import com.lms.lms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class MemberController {


    private final MemberService memberService;


    @GetMapping("/member/register")
    public String register() {

        System.out.println("requestGet");


        return "member/register";
    }

    @RequestMapping(value = "member/register", method = RequestMethod.POST)
    public String registerSubmit(Model model, HttpServletRequest request, MemberInput parameter) {


        boolean result = memberService.register(parameter);
        model.addAttribute("result", result);


        return "member/register_complete";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth(Model model, HttpServletRequest request) {

        String uuid = request.getParameter("id");
        System.out.println(uuid);

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "member/email-auth";


    }

    @GetMapping("/member/info")
    public String memberInfo(Model model,Principal principal) {
        String userId = principal.getName();

        MemberDto detail = memberService.detail(userId);
        model.addAttribute("detail",detail);

        return "member/info";
    }
    @PostMapping("/member/info")
    public String memberInfoSubmit(Model model,MemberInput parameter,Principal principal) {
        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult result = memberService.updateMemberInfo(parameter);
        if(!result.isResult()){
            model.addAttribute("message",result.getMessage());
            return "common/error";
        }


        return "redirect:/member/info";
    }
    @GetMapping("/member/password")
    public String memberPassword(Model model,Principal principal) {
        String userId = principal.getName();

        MemberDto detail = memberService.detail(userId);
        model.addAttribute("detail",detail);

        return "member/password";
    }
    @PostMapping("/member/password")
    public String memberPasswordSubmit(Model model,MemberInput parameter,Principal principal) {

        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult result = memberService.updateMemberPassword(parameter);
        if(!result.isResult()){
            model.addAttribute("message",result.getMessage());
            return "commom/error";
        }


        return "redirect:/member/info";
    }
    @GetMapping("/member/takecourse")
    public String memberTakecourse(Model model,Principal principal) {
        String userId = principal.getName();

        MemberDto detail = memberService.detail(userId);
        model.addAttribute("detail",detail);

        return "member/takecourse";
    }

    @RequestMapping("/member/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/member/find/password")
    public String findPassword() {
        return "member/find_password";
    }

    @PostMapping("/member/find/password")
    public String findPasswordSubmit(Model model, ResetPasswordInput parameter) {
        boolean result = false;
        try {
            result = memberService.sendResetPassword(parameter);

        } catch(Exception e){

        }
        model.addAttribute("result", result);
        return "member/find_password_result";

    }
    @GetMapping("/member/reset/password")
    public String resetPassword(Model model,HttpServletRequest request){
        String uuid = request.getParameter("id");
        model.addAttribute("uuid",uuid);
        boolean result = memberService.checkResetPassword(uuid);
        model.addAttribute("result",result);

        return "member/reset_password";

    }

    @PostMapping("/member/reset/password")
    public String resetPasswordSubmit(Model model, ResetPasswordInput parameter) {
        boolean result = false;
        try {
            result = memberService.resetPassword(parameter.getId(), parameter.getPassword());
        } catch (Exception e) {

        }
        model.addAttribute("result", result);

        return "member/reset_password_result";
    }
}
