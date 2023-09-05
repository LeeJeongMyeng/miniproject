package com.project.sample.dao;

import com.project.sample.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    public int Check_SignUp_name(Member member);
    public int Check_SignUp_email(String email);
    public int Ins_Ctg_Member(Member member);
    public Member SignIn_Ctg_Member(Member member);
    public Member SignIn_Ctg_Email(String email);
}
