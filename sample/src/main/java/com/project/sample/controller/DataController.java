package com.project.sample.controller;

import com.project.sample.dto.ResultDTO;
import com.project.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin(origins = "http://localhost:8800",allowedHeaders = "", methods = {""} )
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
    public ResultDTO editById() {
        return null;

    }

    @DeleteMapping("/delete")
    public ResultDTO delete() {
        return null;

    }

    @PutMapping("/save")
    public ResultDTO save() {
        return null;

    }
}
