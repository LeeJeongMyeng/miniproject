package com.project.sample.service;

import com.project.sample.dto.Member;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface MemberService {
    public int Check_SignUp_email(String email);
    public int Ins_Ctg_Member(Member member) throws IllegalAccessException;
    public Member SignIn_Ctg_Member(Member member);
    public int BN_Check(Member member);
}
