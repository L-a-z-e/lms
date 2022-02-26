package com.lms.lms.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MemberController {

    @GetMapping("/member/register")
    public String register(){

        System.out.println("requestGet");


        return "member/register";
    }

    @RequestMapping(value = "member/register", method = RequestMethod.POST)
    public String registerSubmit(HttpServletRequest request, HttpServletResponse response, MemberInput memberInput){

        System.out.println("requestPost");

        System.out.println(memberInput.toString());



        return "member/register";
    }
}
