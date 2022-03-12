package com.lms.lms.admin.controller;

import com.lms.lms.admin.dto.CategoryDto;
import com.lms.lms.admin.model.CategoryInput;
import com.lms.lms.admin.model.MemberParam;
import com.lms.lms.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/admin/category/list")
    public String list(Model model, MemberParam parameter){
        List<CategoryDto> list = categoryService.list();
        model.addAttribute("list",list);


        return "admin/category/list";
    }

    @PostMapping("/admin/category/add")
    public String add(Model model, CategoryInput parameter){
        boolean result = categoryService.add(parameter.getCategoryName());

        return "redirect:/admin/category/list";
    }

    @PostMapping("/admin/category/delete")
    public String delete(Model model, CategoryInput parameter){

        boolean result = categoryService.delete(parameter.getId());
        return "redirect:/admin/category/list";
    }

    @PostMapping("/admin/category/update")
    public String update(Model model, CategoryInput parameter){

        boolean result = categoryService.update(parameter);
        return "redirect:/admin/category/list";
    }
}
