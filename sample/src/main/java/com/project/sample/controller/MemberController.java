package com.project.sample.controller;


import com.project.sample.service.FleamarketService;
import com.project.sample.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

//회원관련 컨트롤러
@RestController
public class MemberController {

    private final MemberService service;
    @Autowired
    public MemberController(MemberService service) {
        this.service = service;
    }

}
