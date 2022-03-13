package com.lms.lms.course.controller;

import com.lms.lms.admin.service.CategoryService;
import com.lms.lms.course.dto.CourseDto;
import com.lms.lms.course.model.CourseInput;
import com.lms.lms.course.model.CourseParam;
import com.lms.lms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;


    @GetMapping("/course")
    public String course(Model model, CourseParam parameter){

        List<CourseDto> list = courseService.frontList(parameter);
        model.addAttribute("list",list);

        return "/course/index";
    }


    }

