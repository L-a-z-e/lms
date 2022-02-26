package com.lms.lms.main.controller;

import com.lms.lms.component.MailComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MailComponent mailComponent;

    @RequestMapping("/")
    public String index(){
        /*
        String emial = "yysi8771@gmail.com";
        String subject = "제목";
        String text = "<p>p태그</p> <p>테스트</p>";
        mailComponent.sendMail(emial,subject,text);
        */
        return "index";

    }


}
