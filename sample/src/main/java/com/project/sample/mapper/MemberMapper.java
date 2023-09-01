package com.project.sample.mapper;

import com.project.sample.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface MemberMapper {
    @Select("select count(*) from ctg_member where email = #{email}")
    public int Check_SignUp_email(String email);
}
