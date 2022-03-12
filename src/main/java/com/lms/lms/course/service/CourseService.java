package com.lms.lms.course.service;

import com.lms.lms.course.dto.CourseDto;
import com.lms.lms.course.model.CourseInput;
import com.lms.lms.course.model.CourseParam;

import java.util.List;


public interface CourseService {
    boolean add(CourseInput parameter);

    /**
     * 강좌목록
     * @param parameter
     * @return
     */
    List<CourseDto> list(CourseParam parameter);
}
