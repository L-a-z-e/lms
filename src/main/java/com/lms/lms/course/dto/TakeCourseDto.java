package com.lms.lms.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TakeCourseDto {

    long id;

    long courseId;
    String userId;
    long payPrice;
    String status;
    LocalDateTime regDt;

    //JOIN
    String userName;
    String phone;
    String subject;

    //추가 컬럼
    long totalCount;
    long seq;

    public String getRegDtText(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return regDt != null ? regDt.format(formatter) : "";

    }
}
