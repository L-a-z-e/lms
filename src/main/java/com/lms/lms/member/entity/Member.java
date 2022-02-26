package com.lms.lms.member.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Member {
    @Id
    private String userId;
    private String userName;
    private String password;
    private String phone;
    private LocalDateTime registerDate;


    private boolean emailAuthStatus;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;


}
