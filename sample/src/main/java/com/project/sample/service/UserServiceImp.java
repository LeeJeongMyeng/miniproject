package com.project.sample.service;

import com.project.sample.dao.UserDao;
import com.project.sample.dto.ResultDTO;
import com.project.sample.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService{

    private ResultDTO resultDTO;

    private final UserDao dao;
    @Autowired
    public UserServiceImp(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public ResultDTO findAll() {
        resultDTO = new ResultDTO();
        List<UserDTO> resultList = dao.findAll();
        if (resultList != null) {
            resultDTO.setState(true);
            resultDTO.setResult(resultList);
        }else{
            resultDTO.setState(false);
        }
        return null;
    }

    @Override
    public ResultDTO editById(UserDTO userDTO) {
        return null;
    }

    @Override
    public ResultDTO delete(int no) {
        return null;
    }

    @Override
    public ResultDTO save(UserDTO userDTO) {
        return null;
    }
}
