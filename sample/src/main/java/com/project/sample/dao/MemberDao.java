package com.project.sample.dao;

import com.project.sample.dto.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestBody;

@Mapper
public interface MemberDao {
    public int Check_SignUp_email(String email);
    public int Ins_Ctg_Member(Member member);
    public int BN_Check(Member member);
    public Member Ins_Ctg_Member_bs(Member member);
    public Member SignIn_Ctg_Member(Member member);
    public boolean is_business(String user_id);
}
