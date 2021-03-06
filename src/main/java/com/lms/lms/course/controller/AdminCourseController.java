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
public class AdminCourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;


    @GetMapping("/admin/course/list")
    public String list(Model model, CourseParam parameter){
        parameter.init();
        List<CourseDto> courseList = courseService.list(parameter);


        long totalCount=0;
        if(!CollectionUtils.isEmpty(courseList)) {
            totalCount = courseList.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list",courseList);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("pager",pagerHtml);

        return "admin/course/list";
    }

    @GetMapping(value = {"/admin/course/add","/admin/course/edit"})
    public String add(Model model, HttpServletRequest request, CourseInput parameter){
        model.addAttribute("category",categoryService.list());


        boolean editMode = request.getRequestURI().contains("/edit");
        CourseDto detail = new CourseDto();


        if(editMode){
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if(existCourse == null){
                //error??????
                model.addAttribute("message","??????????????? ???????????? ????????????.");
                return "common/error";
            }
            detail = existCourse;

        }
        model.addAttribute("detail",detail);
        model.addAttribute("editMode",editMode);

        return "admin/course/add";
    }

    @PostMapping(value = {"/admin/course/add","/admin/course/edit"})
    public String addSubmit(Model model,HttpServletRequest
            request,CourseInput parameter) {
        boolean editMode = request.getRequestURI().contains("/edit");

        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                //error??????
                model.addAttribute("message", "??????????????? ???????????? ????????????.");
                return "common/error";
            }
            boolean result = courseService.set(parameter);
        }else{
            boolean result = courseService.add(parameter);
        }
            return "redirect:/admin/course/list";
        }
    @PostMapping("/admin/course/delete")
    public String del(Model model,HttpServletRequest
            request,CourseInput parameter) {
        boolean result = courseService.del(parameter.getIdList());

        return "redirect:/admin/course/list";
    }
    }

