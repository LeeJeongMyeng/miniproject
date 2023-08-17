package com.project.sample.dao;

import com.project.sample.mapper.Fmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImp implements MemberDao{

    private final Fmapper mapper;
    @Autowired
    public MemberDaoImp(Fmapper mapper) {
        this.mapper = mapper;
    }

}
