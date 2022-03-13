package com.lms.lms.course.model;

import com.lms.lms.admin.model.CommonParam;
import lombok.Data;

@Data
public class TakeCourseInput extends CommonParam {
    long courseId;
    String userId;

}
