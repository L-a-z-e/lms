package com.lms.lms.admin.service;

import com.lms.lms.admin.dto.CategoryDto;
import com.lms.lms.admin.entity.Category;
import com.lms.lms.admin.model.CategoryInput;
import com.lms.lms.admin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private Sort getSortBySortValueDesc(){
        return Sort.by(Sort.Direction.DESC,"sortValue");
    }
    @Override
    public List<CategoryDto> list() {


        List<Category> categories = categoryRepository.findAll(getSortBySortValueDesc());
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
    public boolean update(CategoryInput parameter){
        Optional<Category> optionalCategory = categoryRepository.findById(parameter.getId());
        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            category.setCategoryName(parameter.getCategoryName());
            category.setSortValue(parameter.getSortValue());
            category.setUsingYn(parameter.isUsingYn());
            categoryRepository.save(category);
        }
        return true;
    }
    public boolean delete(long id){

        categoryRepository.deleteById(id);

        return true;
    }

}
