package com.project.sample.controller;


import com.project.sample.service.MemberService;
import com.project.sample.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

//게시글관련 컨트롤러
@RestController
public class QnaController {

    private final QnaService service;
    @Autowired
    public QnaController(QnaService service) {
        this.service = service;
    }

}
