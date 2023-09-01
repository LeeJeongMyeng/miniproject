package com.project.sample.dao;


import com.project.sample.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImp implements MemberDao{

    private final MemberMapper mapper;
    @Autowired
    public MemberDaoImp(MemberMapper memberMapper) {
        this.mapper = memberMapper;
    }

    @Override
    public int Check_SignUp_email(String email) {
        return mapper.Check_SignUp_email(email);
    }
}
