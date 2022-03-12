package com.lms.lms.admin.service;

import com.lms.lms.admin.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> list();

    boolean add(String categoryName);
    boolean update(CategoryDto parameter);
    boolean del(long id);

}
