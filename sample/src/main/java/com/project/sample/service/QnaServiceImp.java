package com.project.sample.service;

import com.project.sample.dao.MemberDao;
import com.project.sample.dao.QnaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QnaServiceImp implements QnaService {

    private final QnaDao dao;
    @Autowired
    public QnaServiceImp(QnaDao dao) {
        this.dao = dao;
    }

}
