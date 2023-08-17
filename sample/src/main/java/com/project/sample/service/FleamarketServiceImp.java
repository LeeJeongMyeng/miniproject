package com.project.sample.service;

import com.project.sample.dao.FleamarketDao;
import com.project.sample.dao.UserDao;
import com.project.sample.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FleamarketServiceImp implements FleamarketService {

    private final FleamarketDao dao;
    @Autowired
    public FleamarketServiceImp(FleamarketDao dao) {
        this.dao = dao;
    }
}
