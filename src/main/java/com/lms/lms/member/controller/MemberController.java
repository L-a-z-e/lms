package com.lms.lms.member.controller;

import com.lms.lms.member.model.MemberInput;
import com.lms.lms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class MemberController {


    private  final MemberService memberService;


    @GetMapping("/member/register")
    public String register(){

        System.out.println("requestGet");


        return "member/register";
    }

    @RequestMapping(value = "member/register", method = RequestMethod.POST)
    public String registerSubmit(Model model, HttpServletRequest request, MemberInput parameter){



        boolean result = memberService.register(parameter);
        model.addAttribute("result",result);



        return "member/register_complete";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth(Model model, HttpServletRequest request){

        String uuid = request.getParameter("id");
        System.out.println(uuid);

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "member/email-auth";



    }

    @GetMapping("/member/info")
    public String memberInfo(){
        return "member/info";
    }

    @RequestMapping("/member/login")
    public String login(){
        return "member/login";
    }
}
