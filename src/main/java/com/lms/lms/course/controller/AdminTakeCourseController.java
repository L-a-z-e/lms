package com.lms.lms.course.controller;

import com.lms.lms.course.dto.TakeCourseDto;
import com.lms.lms.course.model.TakeCourseParam;
import com.lms.lms.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminTakeCourseController extends BaseController {

    private final TakeCourseService takeCourseService;



    @GetMapping("/admin/takecourse/list")
    public String list(Model model, TakeCourseParam parameter) {
        parameter.init();
        List<TakeCourseDto> courseList = takeCourseService.list(parameter);


        long totalCount = 0;
        if (!CollectionUtils.isEmpty(courseList)) {
            totalCount = courseList.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", courseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);


        return "admin/takecourse/list";
    }

}

