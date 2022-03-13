package com.lms.lms.course.service;

import com.lms.lms.admin.model.ServiceResult;
import com.lms.lms.course.dto.CourseDto;
import com.lms.lms.course.dto.TakeCourseDto;
import com.lms.lms.course.model.CourseInput;
import com.lms.lms.course.model.CourseParam;
import com.lms.lms.course.model.TakeCourseInput;
import com.lms.lms.course.model.TakeCourseParam;

import java.util.List;


public interface TakeCourseService {

    /**
     * 수강목록
     * @param parameter
     * @return
     */
    List<TakeCourseDto> list(TakeCourseParam parameter);

    /**
     * 수강상태 변경
     * @param id
     * @param status
     * @return
     */
    ServiceResult updateStatus(long id, String status);

}
