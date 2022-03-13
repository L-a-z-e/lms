package com.lms.lms.admin.service;

import com.lms.lms.admin.dto.CategoryDto;
import com.lms.lms.admin.model.CategoryInput;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> list();

    boolean add(String categoryName);
    boolean update(CategoryInput parameter);
    boolean delete(long id);

    List<CategoryDto> frontList(CategoryDto parameter);

}
