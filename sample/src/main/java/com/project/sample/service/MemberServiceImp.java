package com.project.sample.service;

import com.project.sample.dao.FleamarketDao;
import com.project.sample.dao.MemberDao;
import com.project.sample.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImp implements MemberService {

    private final MemberDao dao;
    @Autowired
    public MemberServiceImp(MemberDao dao) {
        this.dao = dao;
    }

    @Value("${ctg.Encrypt.Solt}")
    private String CES;


    //이메일 중복검사
    @Override
    public int Check_SignUp_name(Member member) {
        return dao.Check_SignUp_name(member);
    }

    //이름/주민 중복검사
    @Override
    public int Check_SignUp_email(String email) {
        return dao.Check_SignUp_email(email);
    }

    //회원가입
    @Override
    public int Ins_Ctg_Member(Member member) {
        //사업자도 추가해야함..

        return dao.Ins_Ctg_Member(member);
    }


}
