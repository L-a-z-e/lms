package com.lms.lms.admin.mapper;

import com.lms.lms.admin.dto.MemberDto;
import com.lms.lms.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    long selectListCount(MemberParam parameter);
    List<MemberDto> selectList(MemberParam parameter);

}
