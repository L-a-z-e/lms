package com.lms.lms.course.model;

import com.lms.lms.admin.model.CommonParam;
import lombok.Data;

@Data
public class CourseParam extends CommonParam {
    long id;
    long categoryId;

}
