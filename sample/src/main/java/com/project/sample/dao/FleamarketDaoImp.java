package com.project.sample.dao;

import com.project.sample.mapper.FleaMarketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FleamarketDaoImp implements FleamarketDao{

    private final FleaMarketMapper mapper;
    @Autowired
    public FleamarketDaoImp(FleaMarketMapper mapper) {
        this.mapper = mapper;
    }
}
