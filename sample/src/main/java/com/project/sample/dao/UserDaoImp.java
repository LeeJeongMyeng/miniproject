package com.project.sample.dao;

import com.project.sample.dto.UserDTO;
import com.project.sample.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao{

    private final UserMapper userMapper;
    @Autowired
    public UserDaoImp(UserMapper userMapper){
        this.userMapper = userMapper;
    }


    @Override
    public List<UserDTO> findAll() {
        return userMapper.findAll();
    }

    @Override
    public int editById(UserDTO userDTO) {
        return 0;
    }

    @Override
    public int delete(int no) {
        return 0;
    }

    @Override
    public int save(UserDTO userDTO) {
        return 0;
    }
}
