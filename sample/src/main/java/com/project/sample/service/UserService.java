package com.project.sample.service;


import com.project.sample.dto.ResultDTO;
import com.project.sample.dto.UserDTO;

import org.springframework.stereotype.Service;


public interface UserService {

    public ResultDTO findAll();
    public ResultDTO editById(UserDTO userDTO);
    public ResultDTO delete(int no);
    public ResultDTO save(UserDTO userDTO);
}
