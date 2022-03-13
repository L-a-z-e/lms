package com.lms.lms.course.mapper;

import com.lms.lms.course.dto.CourseDto;
import com.lms.lms.course.dto.TakeCourseDto;
import com.lms.lms.course.model.CourseParam;
import com.lms.lms.course.model.TakeCourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TakeCourseMapper {
    long selectListCount(TakeCourseParam parameter);
    List<TakeCourseDto> selectList(TakeCourseParam parameter);

}
