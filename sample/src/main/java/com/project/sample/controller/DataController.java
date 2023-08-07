package com.project.sample.controller;

import com.project.sample.dto.ResultDTO;
import com.project.sample.dto.UserDTO;
import com.project.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin(origins = "http://localhost:8800")
@RestController
public class DataController {

    private final UserService service;

    @Autowired
    public DataController(UserService service) {
        this.service = service;
    }


    @GetMapping("/")
    public String home() {
        return "Data준비중 ...";
    }

    @GetMapping("/api")
    public String api() {
        return "Api 데이터 준비중..";
    }

    @PostMapping("/findAll")
    public ResultDTO findAll() {
        return service.findAll();

    }

    @PostMapping("/editById")
    public ResultDTO editById(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO);
        return service.editById(userDTO);

    }

    @DeleteMapping("/delete")
    public ResultDTO delete(@RequestParam("no") int no) {
        return service.delete(no);

    }

    @PutMapping("/save")
    public ResultDTO save() {
        return null;

    }
}
