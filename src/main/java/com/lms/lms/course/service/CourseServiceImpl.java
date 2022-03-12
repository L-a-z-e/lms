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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public boolean add(CourseInput parameter) {

        Course course = Course.builder()
                .categoryId(parameter.getCategoryId())
                .subject(parameter.getSubject())
                .regDt(LocalDateTime.now())
                .build();
        courseRepository.save(course);

        return true;
    }
    @Override
    public boolean set(CourseInput parameter) {

        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
        if(!optionalCourse.isPresent()){

            return false;
        }
        Course course = optionalCourse.get();
        course.setCategoryId(parameter.getCategoryId());
        course.setSubject(parameter.getSubject());
        course.setUdtDt(LocalDateTime.now());
        courseRepository.save(course);
        return true;
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

    @Override
    public CourseDto getById(long id) {
        return courseRepository.findById(id).map(CourseDto::of).orElse(null);

    }


}
