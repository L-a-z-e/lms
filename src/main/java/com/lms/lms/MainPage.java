package com.lms.lms;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPage {
    @RequestMapping("/")
    public String index(){

        return "Index Page";

    }
    @RequestMapping("/hello")
    public String hello(){

        String msg = " <p>hello</p> <p]>LMS WebSite</p>";
        return msg;
    }

}
