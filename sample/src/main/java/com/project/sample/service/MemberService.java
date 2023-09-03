package com.project.sample.service;

import com.project.sample.dto.Member;

import java.util.Map;

public interface MemberService {
    public int Check_SignUp_name(Member member);
    public int Check_SignUp_email(String email);
}
