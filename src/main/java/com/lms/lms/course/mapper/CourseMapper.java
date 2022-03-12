package com.lms.lms.course.mapper;

import com.lms.lms.course.dto.CourseDto;
import com.lms.lms.course.model.CourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    long selectListCount(CourseParam parameter);
    List<CourseDto> selectList(CourseParam parameter);

}
