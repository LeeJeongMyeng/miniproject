package com.project.sample.service;

import com.project.sample.dao.FleamarketDao;
import com.project.sample.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImp implements MemberService {

    private final MemberDao dao;
    @Autowired
    public MemberServiceImp(MemberDao dao) {
        this.dao = dao;
    }

    @Override
    public int Check_SignUp_email(String email) {
        return dao.Check_SignUp_email(email);
    }
}
