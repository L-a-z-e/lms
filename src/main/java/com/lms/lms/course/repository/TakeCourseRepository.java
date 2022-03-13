package com.lms.lms.course.repository;

import com.lms.lms.course.entity.TakeCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TakeCourseRepository extends JpaRepository<TakeCourse,Long> {


    long countByCourseIdAndUserIdAndStatusIn(long courseId, String userId, Collection<String> statusList);

}
