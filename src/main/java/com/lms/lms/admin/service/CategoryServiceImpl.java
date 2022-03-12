package com.lms.lms.admin.service;

import com.lms.lms.admin.dto.CategoryDto;
import com.lms.lms.admin.entity.Category;
import com.lms.lms.admin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> list() {


        List<Category> categories = categoryRepository.findAll();
        return CategoryDto.of(categories);

        }


    public boolean add(String categoryName){
        Category category = Category.builder()
                .categoryName(categoryName)
                .usingYn(true)
                .sortValue(0)
                .build();
        categoryRepository.save(category);

        return true;

    }
    public boolean update(CategoryDto parameter){
        return true;
    }
    public boolean del(long id){
        return true;
    }

}
