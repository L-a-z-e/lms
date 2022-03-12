package com.lms.lms.course.service;

import com.lms.lms.admin.dto.MemberDto;
import com.lms.lms.course.dto.CourseDto;
import com.lms.lms.course.entity.Course;
import com.lms.lms.course.mapper.CourseMapper;
import com.lms.lms.course.model.CourseInput;
import com.lms.lms.course.model.CourseParam;
import com.lms.lms.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public boolean add(CourseInput parameter) {

        Course course = Course.builder()
                .subject(parameter.getSubject())
                .regDt(LocalDateTime.now())
                .build();
        courseRepository.save(course);

        return false;
    }

    @Override
    public List<CourseDto> list(CourseParam parameter) {

        long totalCount = courseMapper.selectListCount(parameter);
        List<CourseDto> list = courseMapper.selectList(parameter);
        if(!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(CourseDto course : list){
                course.setTotalCount(totalCount);
                course.setSeq(totalCount - parameter.getpageStart() - i);
                i++;
            }
        }
        return list;
    }
}
