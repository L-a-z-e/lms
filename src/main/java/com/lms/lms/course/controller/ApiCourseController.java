package com.lms.lms.course.controller;

import com.lms.lms.admin.model.ServiceResult;
import com.lms.lms.admin.service.CategoryService;
import com.lms.lms.course.model.TakeCourseInput;
import com.lms.lms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class ApiCourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;


    @PostMapping("/api/course/req")
    public ResponseEntity<?> courseReq(Model model, @RequestBody TakeCourseInput parameter, Principal principal){

       parameter.setUserId(principal.getName());
       ServiceResult result = courseService.req(parameter);
       if(!result.isResult()){
           return ResponseEntity.ok().body(result.getMessage());
       }
        return ResponseEntity.ok().body(parameter);


    }




    }

