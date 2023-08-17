package com.project.sample.dao;

import com.project.sample.mapper.Fmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FleamarketDaoImp implements FleamarketDao{

    private final Fmapper mapper;
    @Autowired
    public FleamarketDaoImp(Fmapper mapper) {
        this.mapper = mapper;
    }
}
