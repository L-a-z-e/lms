package com.lms.lms.member.controller;

import com.lms.lms.member.entity.Member;
import com.lms.lms.member.model.MemberInput;
import com.lms.lms.member.repository.MemberRepsitory;
import com.lms.lms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

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
    public String registerSubmit(HttpServletRequest request, HttpServletResponse response, MemberInput parameter){



        boolean result = memberService.register(parameter);


        return "member/register_complete";
    }
}
