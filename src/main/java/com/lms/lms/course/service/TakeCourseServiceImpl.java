package com.lms.lms.course.service;

import com.lms.lms.admin.model.ServiceResult;
import com.lms.lms.course.dto.TakeCourseDto;
import com.lms.lms.course.entity.TakeCourse;
import com.lms.lms.course.mapper.TakeCourseMapper;
import com.lms.lms.course.model.TakeCourseParam;
import com.lms.lms.course.repository.TakeCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TakeCourseServiceImpl implements TakeCourseService {


    private final TakeCourseMapper takeCourseMapper;
    private final TakeCourseRepository takeCourseRepository;




    @Override
    public List<TakeCourseDto> list(TakeCourseParam parameter) {
        long totalCount = takeCourseMapper.selectListCount(parameter);
        List<TakeCourseDto> list = takeCourseMapper.selectList(parameter);
        if(!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(TakeCourseDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getpageStart() - i);
                i++;
            }
        }
        return list;
    }

    @Override
    public ServiceResult updateStatus(long id, String status) {

        Optional<TakeCourse> optionalTakeCourse = takeCourseRepository.findById(id);
        if(!optionalTakeCourse.isPresent()){
            return new ServiceResult(false,"수강 정보가 없습니다.");
        }
        TakeCourse takeCourse = optionalTakeCourse.get();
        takeCourse.setStatus(status);
        takeCourseRepository.save(takeCourse);

        return new ServiceResult(true);
    }
}
