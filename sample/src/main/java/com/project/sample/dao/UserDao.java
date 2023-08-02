package com.project.sample.dao;

import com.project.sample.dto.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserDao {

    public List<UserDTO> findAll();

    public int editById(UserDTO userDTO);

    public int delete(int no);

    public int save(UserDTO userDTO);
}
