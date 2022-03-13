package com.lms.lms.course.service;

import com.lms.lms.admin.model.ServiceResult;
import com.lms.lms.course.dto.CourseDto;
import com.lms.lms.course.model.CourseInput;
import com.lms.lms.course.model.CourseParam;
import com.lms.lms.course.model.TakeCourseInput;

import java.util.List;


public interface CourseService {
    boolean add(CourseInput parameter);

    /**
     * 강좌목록
     * @param parameter
     * @return
     */
    List<CourseDto> list(CourseParam parameter);

    CourseDto getById(long id);

    /**
     * 강좌정보 수정
     * @param parameter
     * @return
     */
    boolean set(CourseInput parameter);

    /**
     * 강좌 삭제
     * @param idList
     * @return
     */
    boolean del(String idList);

    /**
     * 프론트 강좌 목록
     */
    List<CourseDto> frontList(CourseParam parameter);

    /**
     * 프론트 강좌 상세정보
     * @param id
     */
    CourseDto frontDetail(long id);

    /**
     * 수강신청
     * @param parameter
     * @return
     */
    ServiceResult req(TakeCourseInput parameter);
}
