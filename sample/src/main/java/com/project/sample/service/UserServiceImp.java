package com.project.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.sample.dao.UserDao;
import com.project.sample.dto.ResultDTO;
import com.project.sample.dto.UserDTO;

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
        System.out.println(resultDTO.getResult()!=null);
        return resultDTO;
    }

    @Override
    public ResultDTO editById(UserDTO userDTO) {
        resultDTO = new ResultDTO();
        int state = dao.editById(userDTO);
        System.out.println(state);
        if (state == 1) {
            resultDTO.setState(true);
            resultDTO.setMessage("사용자 수정 성공");
        } else {
            resultDTO.setState(false);
            resultDTO.setMessage("사용자 수정 실패");

        }
        return resultDTO;
    }

    @Override
    public ResultDTO delete(int no) {
       resultDTO = new ResultDTO();
        int state = dao.delete(no);
        if (state ==1) {
            resultDTO.setState(true);
            resultDTO.setMessage("삭제성공");
        } else {
            resultDTO.setState(false);
            resultDTO.setMessage("삭제실패");
        }

        return resultDTO;
    }

    @Override
    public ResultDTO save(UserDTO userDTO) {
        resultDTO = new ResultDTO();
        int state = dao.save(userDTO);
        if (state == 1) {
            resultDTO.setState(true);
            resultDTO.setMessage("저장성공");
        } else {
            resultDTO.setState(false);
            resultDTO.setMessage("삭제실패");
        }
        return resultDTO;
    }
}
