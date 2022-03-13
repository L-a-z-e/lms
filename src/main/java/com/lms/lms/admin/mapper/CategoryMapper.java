package com.lms.lms.admin.mapper;

import com.lms.lms.admin.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

  List<CategoryDto> select(CategoryDto parameter);

}
