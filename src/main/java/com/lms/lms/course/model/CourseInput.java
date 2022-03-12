package com.lms.lms.course.model;


import lombok.Data;

@Data
public class CourseInput {
    long id;
    long categoryId;
    String subject;
}
