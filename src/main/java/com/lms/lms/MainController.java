package com.lms.lms;

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

        mailComponent.sendMailTest();
        return "index";

    }


}
